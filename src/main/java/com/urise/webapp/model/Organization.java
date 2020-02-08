package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private OrganizationLink homePage;
    private List<Stages> stages = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, Stages... stages) {
        this(new OrganizationLink(name, url), Arrays.asList(stages));
    }

    public Organization(OrganizationLink homePage, List<Stages> stages) {
        Objects.requireNonNull(homePage, "homePage must not be null");
        Objects.requireNonNull(stages, "stages must not be null");
        this.homePage = homePage;
        this.stages = stages;
    }

    public OrganizationLink getHomePage() {
        return homePage;
    }

    public List<Stages> getStages() {
        return stages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return stages.equals(that.stages);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + stages.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", stages=" + stages +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Stages implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String responsibility;

        public Stages() {
        }

        public Stages(int startYear, Month startMonth, String title, String responsibility) {
            this(of(startYear, startMonth), NOW, title, responsibility);
        }

        public Stages(int startYear, Month startMonth, int endYear, Month endMonth, String title, String responsibility) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, responsibility);
        }

        public Stages(LocalDate startDate, LocalDate endDate, String title, String responsibility) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.responsibility = responsibility;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getResponsibility() {
            return responsibility;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Stages stages = (Stages) o;

            if (!startDate.equals(stages.startDate)) return false;
            if (!endDate.equals(stages.endDate)) return false;
            if (!title.equals(stages.title)) return false;
            return Objects.equals(responsibility, stages.responsibility);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (responsibility != null ? responsibility.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Stages{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", responsibility='" + responsibility + '\'' +
                    '}';
        }
    }
}