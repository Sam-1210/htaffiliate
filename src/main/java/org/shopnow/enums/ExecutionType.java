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

    public boolean equals(ExecutionType other) {
        return other.name.equals(this.name);
    }

    public boolean equals(String other) {
        return this.name.equalsIgnoreCase(other);
    }
}
