package com.urise.webapp.model;

import java.util.Objects;

public class ContentSection<T> implements Section {

    private final T content;

    public ContentSection(T content) {
        Objects.requireNonNull(content, "Content must not be null");
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentSection<?> that = (ContentSection<?>) o;

        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ContentSection{" +
                "content='" + content + '\'' +
                '}';
    }
}
