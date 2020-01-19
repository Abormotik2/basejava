package com.urise.webapp.model;

import java.util.Objects;

public class ContactsOrg {

    private final String name;

    private final String url;

    public ContactsOrg(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactsOrg that = (ContactsOrg) o;

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
