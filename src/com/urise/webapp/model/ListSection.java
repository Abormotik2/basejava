package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> titles;

    public ListSection(List<String> titles) {
        Objects.requireNonNull(titles,"titles must not be null");
        this.titles = titles;
    }

    public List<String> getTitles() {
        return titles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return Objects.equals(titles, that.titles);
    }

    @Override
    public int hashCode() {
        return titles != null ? titles.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "titles=" + titles +
                '}';
    }
}
