package io.codelex.apicalculator.controllers;

import io.codelex.apicalculator.models.Countries;
import io.codelex.apicalculator.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CountryController {

    CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping("/countries")
    Countries getCountries() {
        return service.getCountries();
    }
}
