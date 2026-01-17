package com.example.icts.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient {
    private int age;
    private List<Symptom> symptoms = new ArrayList<>();

    public Patient() {
    }

    public Patient(int age, List<Symptom> symptoms) {
        this.age = age;
        this.symptoms = symptoms;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public void addSymptom(String symptomName) {
        if (this.symptoms == null) {
            this.symptoms = new ArrayList<>();
        }
        this.symptoms.add(new Symptom(symptomName));
    }

    @Override
    public String toString() {
        return "Patient{age=" + age + ", symptoms=" + symptoms + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Patient patient = (Patient) o;
        return age == patient.age && Objects.equals(symptoms, patient.symptoms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, symptoms);
    }
}
