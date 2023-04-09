package com.javaops.webapp.model;

import java.util.List;

public class Company {
    String name;
    String website;
    List<Period> periods;

    public Company(String name, String website, List<Period> periods) {
        this.name = name;
        this.website = website;
        this.periods = periods;
    }
}
