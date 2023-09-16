package com.eternalcode.plots.role;

public record Role(String name) {

    public static final Role NONE = new Role("__NONE__");

}
