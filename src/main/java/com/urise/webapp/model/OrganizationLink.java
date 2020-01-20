package com.urise.webapp.model;

import java.util.Objects;

public class OrganizationLink {

    private final String name;

    private final String url;

    public OrganizationLink(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationLink that = (OrganizationLink) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactsOrg{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
