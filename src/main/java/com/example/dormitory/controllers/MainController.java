package com.example.dormitory.controllers;

import com.example.dormitory.DAO.AdminDAO;
import com.example.dormitory.DAO.StudentDAO;
import com.example.dormitory.models.Admin;
import com.example.dormitory.models.Priority;
import com.example.dormitory.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/")
public class MainController {

    private final StudentDAO studentDAO;
    private final AdminDAO adminDAO;

    private List<Student[]> groups = new ArrayList<>();

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
        model.addAttribute("students", groups);
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
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/groupViewAdmin")
    public String groupView(Model model) {
        int amountGroups = 0;
        int percentOfBudget = 0;

        model.addAttribute("amountOfGroups", amountGroups);
        model.addAttribute("percentOfBudget", percentOfBudget);
        return "view/groupViewAdmin";
    }

    @RequestMapping(value = "/groupViewAdmin", method = RequestMethod.POST)
    public String getParametersForGroups(@ModelAttribute(value = "amountOfGroups") int amountGroups,
                                         @ModelAttribute(value = "percentOfBudget") double percentOfBudget) {
        groups.clear();
        List<Student> listToWork = List.copyOf(studentDAO.index());

        percentOfBudget = percentOfBudget / 100;
        System.out.println("percentOfBudget " + percentOfBudget);
        System.out.println("amountOfGroups " + amountGroups);

        for (int i = 0; i < amountGroups; i++) {

            Student[] group;

            if (i + 1 == amountGroups) {
                group = new Student[listToWork.size() / amountGroups + listToWork.size() % amountGroups];
            } else {
                group = new Student[listToWork.size() / amountGroups];
            }

            for (int j = 0; j < group.length * percentOfBudget; j++) {
                group[j] = listToWork.stream()
                        .filter(student -> student.getPriority().equals("BUDGET") && !student.isInGroup())
                        .findFirst()
                        .orElse(new Student(0, "test", "test", 100, "Budget"))
                        .setInGroup(true);
                System.out.println(group[j].getFirstName() + " " + group[j].getLastName());
            }

            System.out.println("group.length * percentOfBudget " + group.length * percentOfBudget);
            for (int j = (int) (group.length * percentOfBudget); j < group.length; j++) {
                group[j] = listToWork.stream()
                        .filter(student -> student.getPriority().equals("CONTRACT") && !student.isInGroup())
                        .findFirst()
                        .orElse(new Student(0, "test", "test", 100, "Contract"))
                        .setInGroup(true);
                System.out.println(group[j].getFirstName() + " " + group[j].getLastName());
            }
            groups.add(group);

        }
        return "redirect:/groupView";
    }
}
