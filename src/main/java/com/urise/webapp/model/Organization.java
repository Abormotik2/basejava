package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization  {

    private final OrganizationLink homePage;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final String title;

    private String responsibility;

    public Organization(OrganizationLink homePage, LocalDate startDate, LocalDate endDate, String title, String responsibility) {
        Objects.requireNonNull(homePage, "homePage must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homePage = homePage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.responsibility = responsibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!title.equals(that.title)) return false;
        return Objects.equals(responsibility, that.responsibility);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (responsibility != null ? responsibility.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", responsibility='" + responsibility + '\'' +
                '}';
    }
}