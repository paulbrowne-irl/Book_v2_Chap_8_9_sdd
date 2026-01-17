package com.example.icts.model;

import java.util.Objects;

public class Symptom {
    private String name;

    public Symptom() {
    }

    public Symptom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Symptom{name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Symptom symptom = (Symptom) o;
        return Objects.equals(name, symptom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
