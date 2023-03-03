package io.codelex.apicalculator.service;

import io.codelex.apicalculator.models.Countries;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CountryService {
    public Countries getCountries() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://restcountries.com/v3.1/all";
        String response = restTemplate.getForObject(url, String.class);
        return new Countries(createCountryList(response));
    }

    private List<String> createCountryList(String response) {
        JSONArray list = new JSONArray(response);
        List<String> countryList = new ArrayList<>();
        for (int i = 0; i < list.length(); i++) {
            String countryName = list.getJSONObject(i)
                                     .getJSONObject("name")
                                     .get("common")
                                     .toString();
            countryList.add(countryName);
        }
        Collections.sort(countryList);
        return countryList;
    }
}
