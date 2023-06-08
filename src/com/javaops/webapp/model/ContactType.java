package com.javaops.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Номер телефона"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return getConnectWay() + ": " + toLink("skype: " + value, value);
        }
    },
    EMAIL("Email") {
        @Override
        public String toHtml0(String value) {
            return getConnectWay() + ": " + toLink("mailto: " + value, value);
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль на GitHub") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль на StackOverFlow");

    ContactType(String connectWay) {
        this.connectWay = connectWay;
    }

    private String connectWay;

    public String getConnectWay() {
        return connectWay;
    }

    protected String toHtml0(String value) {
        return connectWay + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, connectWay);
    }

    public static String toLink(String href, String connectWay) {
        return "<a href='" + href + "'>" + connectWay + "</a>";
    }
}

