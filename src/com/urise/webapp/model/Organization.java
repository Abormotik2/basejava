package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Organization extends OrganizationSection {

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final String title;

    private final String responsibility;

    public Organization(List<Organization> organization, LocalDate startDate, LocalDate endDate, String title, String responsibility) {
        super(organization);
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(responsibility, "responsibility must not be null");
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

        if (!Objects.equals(startDate, that.startDate)) return false;
        if (!Objects.equals(endDate, that.endDate)) return false;
        if (!Objects.equals(title, that.title)) return false;
        return Objects.equals(responsibility, that.responsibility);
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (responsibility != null ? responsibility.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", responsibility='" + responsibility + '\'' +
                '}';
    }
}
