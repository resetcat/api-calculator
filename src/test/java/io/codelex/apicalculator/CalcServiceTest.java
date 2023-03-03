package io.codelex.apicalculator;

import io.codelex.apicalculator.models.CalcInput;
import io.codelex.apicalculator.service.CalcService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CalcServiceTest {
    @InjectMocks
    CalcService calcService;

    @Test
    public void testCalculateAddition() {
        CalcInput input = new CalcInput(5, 7, "+");
        Number result = calcService.calculate(input);
        assertEquals(12, result);
    }

    @Test
    public void testCalculateSubtraction() {
        CalcInput input = new CalcInput(10, 4, "-");
        Number result = calcService.calculate(input);
        assertEquals(6, result);
    }

    @Test
    public void testCalculateMultiplication() {
        CalcInput input = new CalcInput(3, 6, "*");
        Number result = calcService.calculate(input);
        assertEquals(18, result);
    }

    @Test
    public void testCalculateDivision() {
        CalcInput input = new CalcInput(20, 5, "/");
        Number result = calcService.calculate(input);
        assertEquals(4, result);
    }

    @Test
    public void testCalculateInvalidOperation() {
        CalcInput input = new CalcInput(4, 5, "invalid");
        Number result = calcService.calculate(input);
        assertEquals(0, result);
    }

    @Test
    public void testSimpleNumber() {
        // Test whole number input
        double wholeNumber = 5;
        Number expectedWhole = 5;
        Number actualWhole = calcService.simpleNumber(wholeNumber);
        assertEquals(expectedWhole, actualWhole);

        // Test decimal input
        double decimalNumber = 7.12345;
        Number expectedDecimal = 7.12;
        Number actualDecimal = calcService.simpleNumber(decimalNumber);
        assertEquals(expectedDecimal, actualDecimal);
    }

    @Test
    void testValidateCalcInputWithValidInputsShouldNotThrowException() {
        List<CalcInput> inputs = Arrays.asList(
                new CalcInput(5, 3, "+"),
                new CalcInput(8, 2, "-")
        );
        assertDoesNotThrow(() -> calcService.validateCalcInput(inputs));
    }

    @Test
    void testValidateCalcInputWithTooManyInputsShouldThrowBadRequestException() {
        List<CalcInput> inputs = Arrays.asList(
                new CalcInput(5, 3, "+"),
                new CalcInput(8, 2, "-"),
                new CalcInput(1, 1, "*")
        );
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                                                         () -> calcService.validateCalcInput(inputs));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("only 2 calculation are available at the same time", exception.getReason());
    }

    @Test
    void testValidateCalcInputWithInvalidXShouldThrowBadRequestException() {
        List<CalcInput> inputs = Arrays.asList(
                new CalcInput(-5, 3, "+"),
                new CalcInput(12, 2, "-")
        );
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                                                         () -> calcService.validateCalcInput(inputs));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("invalid integer", exception.getReason());
    }

    @Test
    void testValidateCalcInputWithInvalidYShouldThrowBadRequestException() {
        List<CalcInput> inputs = Arrays.asList(
                new CalcInput(5, -3, "+"),
                new CalcInput(8, 12, "-")
        );
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                                                         () -> calcService.validateCalcInput(inputs));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("invalid integer", exception.getReason());
    }

    @Test
    void testValidateCalcInputWithInvalidOperatorShouldThrowBadRequestException() {
        List<CalcInput> inputs = Arrays.asList(
                new CalcInput(5, 3, "%"),
                new CalcInput(8, 2, "&")
        );
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                                                         () -> calcService.validateCalcInput(inputs));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("invalid operator", exception.getReason());
    }

    @Test
    void testValidateCalcInputWithDivisionByZeroShouldThrowBadRequestException() {
        List<CalcInput> inputs = Arrays.asList(
                new CalcInput(5, 0, "/"),
                new CalcInput(8, 0, "/")
        );
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                                                         () -> calcService.validateCalcInput(inputs));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Cannot divide by zero", exception.getReason());
    }

    @Test
    void testValidateStringList_ValidInput() {
        List<String> input = Arrays.asList("1+2*3-4", "5/6+7", "8-9*0");
        assertDoesNotThrow(() -> calcService.validateStringList(input));
    }

    @Test
    void testValidateStringList_InvalidFormat() {
        List<String> input = Arrays.asList("1+2*3-4", "5/6+7", "18-9");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                                                         () -> calcService.validateStringList(input));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Invalid input format", exception.getReason());
    }

    @Test
    void testValidateStringList_InvalidCharacter() {
        List<String> input = Arrays.asList("1+2*3-4", "5/6+7", "8-9x0");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                                                         () -> calcService.validateStringList(input));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Invalid input format", exception.getReason());
    }

    @Test
    void testValidateStringList_DivisionByZero() {
        List<String> input = Arrays.asList("1+2*3-4", "5/0+7", "8-9*0");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                                                         () -> calcService.validateStringList(input));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Cannot divide by zero", exception.getReason());
    }
}
