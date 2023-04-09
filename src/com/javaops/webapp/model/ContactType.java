package com.javaops.webapp.model;

public enum ContactType {
    PHONE_NUMBER("number of phone"),
    SKYPE("skype link"),
    EMAIL("email link"),
    LINKEDIN("linkedIn link"),
    GITHUB("Github link"),
    STACKOVERFLOW("StackOverFlow link");

    ContactType(String connectWay) {
        this.connectWay = connectWay;
    }

    private String connectWay;

}
