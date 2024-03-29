package org.shopnow.enums;

public enum Environment {
    QA("qa"),
    PREPROD("preprod"),
    PROD("prod");

    final String name;

    Environment(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getURL() {
        String URL = "";
        switch (this) {
            case QA:
                URL = "http://ht-affiliate-qa.hindustantimes.com";
                break;
            case PREPROD:
                URL = "http://ht-affiliate-preprod.hindustantimes.com";
                break;
            case PROD:
                URL = "https://shopnow.hindustantimes.com";
                break;
        }
        return URL;
    }
}
