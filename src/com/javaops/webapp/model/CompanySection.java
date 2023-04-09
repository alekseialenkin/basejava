package com.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class CompanySection extends AbstractSection {
    private List<Company> companies = new ArrayList<>();

    public CompanySection(List<Company> companies) {
        this.companies = companies;
    }
}
