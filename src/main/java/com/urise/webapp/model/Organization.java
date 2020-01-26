package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final OrganizationLink homePage;

    private  List<Stages> stages;

    public Organization(OrganizationLink homePage, List<Stages> stages) {
        Objects.requireNonNull(homePage, "homePage must not be null");
        Objects.requireNonNull(stages, "stages must not be null");
        this.homePage = homePage;
        this.stages = stages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return stages.equals(that.stages);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + stages.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", stages=" + stages +
                '}';
    }
}