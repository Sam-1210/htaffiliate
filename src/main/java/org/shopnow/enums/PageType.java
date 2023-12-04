package org.shopnow.enums;

public enum PageType {
    SECTION("section"), SUBSECTION("subsection"), STORY("story");

    final String name;

    PageType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(PageType other) {
        return other.name.equals(this.name);
    }

    public boolean equals(String other) {
        return this.name.equalsIgnoreCase(other);
    }
}
