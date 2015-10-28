package com.hieugmail.hieu.service;

/**
 * Define environment.
 */
public enum Environment {
    DEVELOP("https://www.google.com.vn");
    private final String url;

    Environment(String val) {
        url = val;
    }

    public String getUrl() {
        return url;
    }
}
