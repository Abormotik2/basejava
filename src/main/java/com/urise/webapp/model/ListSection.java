package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection implements Section {
    private final List<ContentSection> items;

    public ListSection(List<ContentSection> items) {
        Objects.requireNonNull(items, "titles must not be null");
        this.items = items;
    }

    public List<ContentSection> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "titles=" + items +
                '}';
    }
}
