package com.example.dormitory.models;

public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private double points;
    private Priority priority;

    public Student(int id, String firstName, String lastName, double points, Priority priority) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}

