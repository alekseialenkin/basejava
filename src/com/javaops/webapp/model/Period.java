package com.javaops.webapp.model;

import java.time.LocalDate;

public class Period {
    LocalDate begin;
    LocalDate end;
    String title;
    String description;

    public Period(LocalDate begin, LocalDate end, String title, String description) {
        this.begin = begin;
        this.end = end;
        this.title = title;
        this.description = description;
    }
}
