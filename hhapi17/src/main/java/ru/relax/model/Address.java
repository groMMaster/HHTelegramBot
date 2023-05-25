package ru.relax.model;

import lombok.Getter;
import lombok.Setter;

public class Address {
    @Getter
    @Setter
    private String raw;

    public String toString() {
        return raw;
    }
}
