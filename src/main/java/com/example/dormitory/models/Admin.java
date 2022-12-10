package com.example.dormitory.models;

import java.util.Objects;

public class Admin {
    private String password;

    public Admin() {}

    public Admin(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(password, admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
