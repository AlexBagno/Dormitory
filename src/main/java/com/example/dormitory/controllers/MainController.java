package com.example.dormitory.controllers;

import com.example.dormitory.DAO.AdminDAO;
import com.example.dormitory.DAO.StudentDAO;
import com.example.dormitory.models.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

    private final StudentDAO studentDAO;
    private final AdminDAO adminDAO;

    @Autowired
    public MainController(StudentDAO studentDAO, AdminDAO adminDAO) {
        this.studentDAO = studentDAO;
        this.adminDAO = adminDAO;
    }

    @GetMapping()
    public String chosePage(Model model) {
        return "index";
    }

    @GetMapping("/groupView")
    public String userView(Model model) {
        model.addAttribute("students", studentDAO.index());
        return "view/groupView";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        Admin admin = new Admin(null);
        model.addAttribute("admin", admin);
        return "pages/admin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String checkPass(@ModelAttribute(value = "admin") Admin admin) {
        if (adminDAO.index().stream().anyMatch(admin1 -> admin1.equals(admin))) {
            return "redirect:/groupViewAdmin";
        }
        else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/groupViewAdmin")
    public String groupView(Model model) {
        model.addAttribute("students", studentDAO.index());
        return "view/groupViewAdmin";
    }
}
