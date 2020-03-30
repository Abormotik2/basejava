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

    public static SectionType parseOf(String keyParam) {
        for (SectionType value : SectionType.values()) {
            if (value.name().equals(keyParam)) {
                return value;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }
}