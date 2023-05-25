package ru.relax.model;

import lombok.Getter;
import lombok.Setter;

public class Salary {
    @Getter
    @Setter
    private Integer from;
    @Getter
    @Setter
    private Integer to;
    @Getter
    @Setter
    private String currency;

    public String toString() {
        String value;
        if (from == null && to == null) {
            return "";
        } else if (from == null) {
            value = "до " + String.valueOf(to);
        } else if (to == null) {
            value = "от " + String.valueOf(from);
        } else if (from.equals(to)) {
            value = String.valueOf(from);
        } else {
            value = from + " - " + to;
        }

        if (currency.equals("RUR")) {
            return value + " руб.";
        }

        return value + currency;
    }
}
