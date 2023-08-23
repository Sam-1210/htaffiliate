package org.shopnow.enums;

public enum Environment {
    QA("qa"),
    PRE_PROD("preprod"),
    PROD("prod");

    final String name;

    Environment(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
