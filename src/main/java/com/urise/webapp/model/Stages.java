package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Stages {

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final String title;

    private String responsibility;

    public Stages(LocalDate startDate, LocalDate endDate, String title, String responsibility) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.responsibility = responsibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stages stages = (Stages) o;

        if (!startDate.equals(stages.startDate)) return false;
        if (!endDate.equals(stages.endDate)) return false;
        if (!title.equals(stages.title)) return false;
        return Objects.equals(responsibility, stages.responsibility);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (responsibility != null ? responsibility.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Stages{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", responsibility='" + responsibility + '\'' +
                '}';
    }
}