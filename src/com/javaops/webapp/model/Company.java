package com.javaops.webapp.model;

import com.google.gson.annotations.JsonAdapter;
import com.javaops.webapp.util.DateUtil;
import com.javaops.webapp.util.XmlLocalDateAdapter;
import com.javaops.webapp.util.JsonLocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.javaops.webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Link website;
    private List<Period> periods;

    public Company() {
    }

    public Company(String name, String url, Period... periods) {
        this(new Link(name, url), Arrays.asList(periods));
    }

    public Company(Link website, List<Period> periods) {
        Objects.requireNonNull(website, "website must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.website = website;
        this.periods = periods;
    }

    public Link getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        return "Company{" +
                ", website='" + website + '\'' +
                ", periods=" + periods +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(website, company.website) && Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(website, periods);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        @JsonAdapter(JsonLocalDateAdapter.class)
        private LocalDate begin;
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        @JsonAdapter(JsonLocalDateAdapter.class)
        private LocalDate end;
        private String title;
        private String description;

        public Period() {
        }

        public Period(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), NOW, title, description);
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

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
            return "Period(" + begin + "," + end + "," + title + "," + description + ")";
        }
    }
}
