package com.urise.webapp.model;

import sun.plugin.javascript.navig4.Link;

import java.util.Objects;

public class Contacts {

    private final String name;

    private final Link url;

    public Contacts(String name, Link url) {
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(url, "url must not be null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public Link getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacts contacts = (Contacts) o;

        if (!Objects.equals(name, contacts.name)) return false;
        return Objects.equals(url, contacts.url);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "name='" + name + '\'' +
                ", url=" + url +
                '}';
    }
}
