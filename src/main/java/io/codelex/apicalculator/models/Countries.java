package io.codelex.apicalculator.models;

import java.util.List;

public class Countries {
    List<String> countries;

    public Countries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getCountries() {
        return countries;
    }
}
