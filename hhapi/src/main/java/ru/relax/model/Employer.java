package ru.relax.model;

import lombok.Getter;
import lombok.Setter;

public class Employer {
    @Getter
    @Setter
    private String name;

    public String toString() {
        return name;
    }
}
