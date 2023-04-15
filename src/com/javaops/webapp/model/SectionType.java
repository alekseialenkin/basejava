package com.javaops.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");
    private String title;
    private static final long serialVersionUID =1L;
    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
