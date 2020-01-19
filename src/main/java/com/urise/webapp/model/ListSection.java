package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection<T extends ContentSection<?>> implements Section {
    private final List<T> items;

    public ListSection(List<T> items) {
        Objects.requireNonNull(items, "titles must not be null");
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection<?> that = (ListSection<?>) o;

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
