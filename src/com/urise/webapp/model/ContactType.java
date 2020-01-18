package com.urise.webapp.model;

public enum ContactType {

    MOBILE("Мобильный телефон"),
    SKYPE("Скайп"),
    MAIL("Почта"),
    LinkedIn("LinkedIn"),
    GITHUB("GIT"),
    StackOverflow("StackOverflow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}