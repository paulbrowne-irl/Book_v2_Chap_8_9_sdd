package com.example.icts.model;

import java.util.Objects;

public class TriageResult {
    private String color;
    private int priority;

    public TriageResult() {
    }

    public TriageResult(String color, int priority) {
        this.color = color;
        this.priority = priority;
    }

    public TriageResult(String color) {
        this.color = color;
        this.priority = calculatePriority(color);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    private int calculatePriority(String color) {
        return switch (color.toLowerCase()) {
            case "red" -> 1;
            case "orange" -> 2;
            case "yellow" -> 3;
            case "green" -> 4;
            case "blue" -> 5;
            default -> 5;
        };
    }

    @Override
    public String toString() {
        return "TriageResult{color='" + color + "', priority=" + priority + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TriageResult that = (TriageResult) o;
        return priority == that.priority && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, priority);
    }
}
