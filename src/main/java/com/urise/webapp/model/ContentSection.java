package com.urise.webapp.model;

import java.util.Objects;

public class ContentSection extends Section {
    private static final long serialVersionUID = 1L;
    public static final ContentSection EMPTY_CONTENT = new ContentSection("");
    private String content;

    public ContentSection() {
    }

    public ContentSection(String content) {
        Objects.requireNonNull(content, "Content must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentSection that = (ContentSection) o;

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
