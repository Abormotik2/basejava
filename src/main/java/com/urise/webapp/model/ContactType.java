package com.urise.webapp.model;

public enum ContactType {

    MOBILE("Тел: "),
    SKYPE("Skype: ") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + "<a href='Skype: " + value + "'>" + value + "</a>";
        }
    },
    MAIL("Почта: ") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + "<a href='Mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='LinkedId:" + value + "'>" + value + "</a>";
        }
    },
    GITHUB("Профиль GitHub: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='GitHub:" + value + "'>" + value + "</a>";
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='Stackoverflow:" + value + "'>" + value + "</a>";
        }
    },
    HOME_PAGE("Домашняя страница: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='Home_Page:" + value + "'>" + value + "</a>";
        }
    };

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}