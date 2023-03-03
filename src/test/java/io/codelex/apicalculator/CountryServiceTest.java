package io.codelex.apicalculator;

import io.codelex.apicalculator.service.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {
    @InjectMocks
    CountryService countryService;


    @Test
    void testCreateCountryList() {
        // given
        String response = "[{\"name\":{\"common\":\"Finland\",\"official\":\"Republic of Finland\"}},"
                + "{\"name\":{\"common\":\"India\",\"official\":\"Republic of India\"}},"
                + "{\"name\":{\"common\":\"Australia\",\"official\":\"Commonwealth of Australia\"}}]";

        // when
        List<String> countryList = countryService.createCountryList(response);

        // then
        List<String> expectedCountryList = Arrays.asList("Australia", "Finland", "India");
        assertEquals(expectedCountryList, countryList);
    }

    @Test
    void testCreateCountryListWithEmptyResponse() {
        // given
        String response = "[]";

        // when
        List<String> countryList = countryService.createCountryList(response);

        // then
        List<String> expectedCountryList = List.of();
        assertEquals(expectedCountryList, countryList);
    }
}
