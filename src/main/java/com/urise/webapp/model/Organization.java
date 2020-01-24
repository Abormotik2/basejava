package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final OrganizationLink homePage;

    private  List<Stages> stage;

    public Organization(OrganizationLink homePage, List<Stages> stage) {
        Objects.requireNonNull(homePage, "homePage must not be null");
        Objects.requireNonNull(stage, "stage must not be null");
        this.homePage = homePage;
        this.stage = stage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return stage.equals(that.stage);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + stage.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", stage=" + stage +
                '}';
    }
}