package io.codelex.apicalculator.controllers;

import io.codelex.apicalculator.models.CalcInput;
import io.codelex.apicalculator.models.MinMaxInteger;
import io.codelex.apicalculator.service.CalcService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
class CalcController {

    CalcService service;

    public CalcController(CalcService service) {
        this.service = service;
    }

    @GetMapping("/task1")
    List<Number> calculateFromIntegers(@RequestBody List<CalcInput> input) {
        return service.calcResults(input);
    }

    @GetMapping("/task2")
    List<Number> calculateFromString(@RequestBody List<String> input) {
        return service.calcFromStrings(input);
    }

    @GetMapping("/task3")
    List<MinMaxInteger> calculateFromArray(@RequestBody List<List<Integer>> input) {
        return service.getMinMaxInt(input);
    }
}
