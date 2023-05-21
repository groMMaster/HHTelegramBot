package ru.relax.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Vacancy {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String alternate_url;
    @Getter
    @Setter
    private Address address;
    @Getter
    @Setter
    private Employer employer;
    @Getter
    @Setter
    private Salary salary;

    public String toString() {
        return name + "\n"
                + (salary != null ? salary.toString() + "\n": "")
                + employer.toString() + "\n"
                + (address != null ? address.toString() + "\n": "")
                + alternate_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacancy)) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(getId(), vacancy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
