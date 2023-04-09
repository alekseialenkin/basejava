package com.javaops.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private LocalDate begin;
    private LocalDate end;
    private String title;
    private String description;

    public Period(LocalDate begin, LocalDate end, String title, String description) {
        this.begin = begin;
        this.end = end;
        this.title = title;
        this.description = description;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(begin, period.begin) && Objects.equals(end, period.end) && Objects.equals(title, period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end, title, description);
    }

    @Override
    public String toString() {
        return "Period{" +
                "begin=" + begin +
                ", end=" + end +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
