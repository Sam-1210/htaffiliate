package org.shopnow.enums;

public enum POM {
    PAGES("pages"), COMPONENTS("components");
    final String name;

    POM(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
