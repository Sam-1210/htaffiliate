package org.shopnow.enums;

public enum Platforms {
    WEB("WEB"), MWEB("MWEB"), AMP("AMP");

    final String name;

    Platforms(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
