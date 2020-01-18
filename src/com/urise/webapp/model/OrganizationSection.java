package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection implements Section {

    private final List<Organization> organization;

    public OrganizationSection(List<Organization> organization) {
        this.organization = organization;
    }

    public List<Organization> getOrganization() {
        Objects.requireNonNull(organization, "organization must not be null");
        return organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {
        return organization != null ? organization.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "organization=" + organization +
                '}';
    }
}
