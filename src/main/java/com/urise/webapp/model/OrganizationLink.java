package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationLink implements Serializable {
    private static final  long serialVersionUID = 1L;

    private String name;

    private String url;

    public OrganizationLink() {
    }

    public OrganizationLink(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url == null ? "" : url;
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
