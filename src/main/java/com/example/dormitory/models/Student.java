package com.example.dormitory.models;



public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private double points;
    private Priority priority;

    private boolean isInGroup = false;

    public Student() {}

    public Student(int id, String firstName, String lastName, double points, String priority) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
        try {
            this.priority = Priority.valueOf(priority.toUpperCase());
        } catch (Exception e) {
            this.priority = Priority.CONTRACT;
        }
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

    public String getPriority() {
        return priority.toString();
    }

    public void setPriority(String priority) {
        this.priority = Priority.valueOf(priority.toUpperCase());
    }

    public boolean isInGroup() {
        return isInGroup;
    }

    public Student setInGroup(boolean inGroup) {
        if (this == null) {
            throw new NullPointerException("There is no enough students");
        }
        isInGroup = inGroup;
        return this;
    }
}

