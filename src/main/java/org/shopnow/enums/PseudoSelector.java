package org.shopnow.enums;

public enum PseudoSelector {
    BEFORE("::before"),
    AFTER("::after");

    final String name;

    PseudoSelector(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
