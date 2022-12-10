package com.example.dormitory.DAO;

import com.example.dormitory.models.Admin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminDAO {
    private List<Admin> admins;

    {
        admins = new ArrayList<>();

        admins.add(new Admin("1234"));
    }

    public List<Admin> index() {
        return admins;
    }
}
