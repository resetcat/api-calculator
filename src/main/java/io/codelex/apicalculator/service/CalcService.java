package io.codelex.apicalculator.service;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import io.codelex.apicalculator.models.CalcInput;
import io.codelex.apicalculator.models.MinMaxInteger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CalcService {
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public List<Number> calcResults(List<CalcInput> requests) {
        validateCalcInput(requests);
        List<Number> list = new ArrayList<>();
        requests.forEach(calc -> list.add(calculate(calc)));
        return list;
    }

    public Number calculate(CalcInput input) {
        int x = input.x();
        int y = input.y();
        String operation = input.operation();
        double result = switch (operation) {
            case "+" -> x + y;
            case "-" -> x - y;
            case "*" -> x * y;
            case "/" -> (double) x / y;
            default -> 0;
        };
        return simpleNumber(result);
    }

    public Number simpleNumber(double result) {
        if (result == (int) result) {
            return (int) result;
        } else {
            return Double.parseDouble(decimalFormat.format(result).replaceAll(",", "."));
        }
    }


    public void validateCalcInput(List<CalcInput> inputs) {
        if (inputs.size() > 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "only 2 calculation are " +
                    "available at the same time");
        }
        for (CalcInput input : inputs) {
            int x = input.x();
            int y = input.y();
            String operation = input.operation();
            checkDivisionByZero(operation + y);
            if (x < 0 || x > 9 || y < 0 || y > 9) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid integer");
            }
            if (operation.length() != 1 || !operation.matches("[+*-/]")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid operator");
            }
        }
    }

    public List<Number> calcFromStrings(List<String> input) {
        validateStringList(input);
        DoubleEvaluator eval = new DoubleEvaluator();
        return input.stream().map(eval::evaluate).map(this::simpleNumber).toList();
    }

    public void validateStringList(List<String> input) {
        for (String string : input) {
            checkDivisionByZero(string);
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                if (i % 2 == 0 && !Character.isDigit(c)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                      "Invalid input format");
                } else if (i % 2 == 1 && c != '*' && c != '/' && c != '+' && c != '-') {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                      "Invalid input format");
                }
            }
        }
    }

    private void checkDivisionByZero(String string) {
        if (string.contains("/0")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot divide by zero");
        }
    }

    public List<MinMaxInteger> getMinMaxInt(List<List<Integer>> input) {
        List<MinMaxInteger> minMaxList = new ArrayList<>();
        for (List<Integer> list : input) {
            validateList(list);
            list.sort(Collections.reverseOrder());
            minMaxList.add(new MinMaxInteger(list.get(0), list.get(list.size() - 1)));
        }
        return minMaxList;
    }

    private void validateList(List<Integer> list) {
        if (list.size() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "array is too long");
        }
    }
}
