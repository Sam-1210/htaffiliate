package org.shopnow.enums;

public enum ExecutionType {
    SANITY("sanity"), REGRESSION("regression");

    final String name;

    ExecutionType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
