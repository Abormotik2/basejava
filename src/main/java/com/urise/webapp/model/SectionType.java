package com.urise.webapp.model;

public enum SectionType {

    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATION("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(Section value) {
        return title + ": " + value;
    }

    public String toHtml(Section value) {
        return (value == null) ? "" : toHtml0(value);
    }
}