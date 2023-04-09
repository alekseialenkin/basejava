package com.javaops.webapp.model;

import java.util.List;

public class CompanySection extends AbstractSection {
    List<Company> companies;

    public CompanySection(List<Company> companies) {
        this.companies = companies;
    }
}
