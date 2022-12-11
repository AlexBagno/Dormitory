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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MainController {

    private final StudentDAO studentDAO;
    private final AdminDAO adminDAO;

    private static boolean CHECK_ADMIN = false;

    private List<List<Student>> groups = new ArrayList<>();

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
        if (groups.isEmpty()) {
            return "redirect:/";
        }
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
            CHECK_ADMIN = true;
            return "redirect:/groupViewAdmin";
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/groupViewAdmin")
    public String groupView(Model model) {
        if (!CHECK_ADMIN)
            return "redirect:/admin";
        int amountGroups = 0;
        double percentOfBudget = 0;

        model.addAttribute("amountOfGroups", amountGroups);
        model.addAttribute("percentOfBudget", percentOfBudget);
        return "pages/adminPanel";
    }

    @RequestMapping(value = "/groupViewAdmin", method = RequestMethod.POST)
    public String getParametersForGroups(@ModelAttribute(value = "amountOfGroups") int amountGroups,
                                         @ModelAttribute(value = "percentOfBudget") double percentOfBudget) {
        groups.clear();
        List<Student> listToWork = List.copyOf(studentDAO.index());

        List<Student> listOfBudget = List.copyOf(studentDAO.index().stream()
                .filter(student -> student.getPriority().equals("BUDGET"))
                .collect(Collectors.toList()));

        List<Student> listOfContract = List.copyOf(studentDAO.index().stream()
                .filter(student -> student.getPriority().equals("CONTRACT"))
                .collect(Collectors.toList()));

        percentOfBudget = percentOfBudget / 100;
        System.out.println("percentOfBudget = " + percentOfBudget);

        int studentsPerGroup = listToWork.size() / amountGroups;
        int remainingStudents = listToWork.size() % amountGroups;

        for (int i = 1; i <= amountGroups; i++) {
            int extra = i <= remainingStudents ? 1 : 0;
            System.out.println("group " + i + " contains " + (studentsPerGroup + extra) + " students.");

            Student[] group = new Student[studentsPerGroup + extra];

            for (int j = 0; j < (int) (group.length * percentOfBudget); j++) {
                int finalJ = j;
                listOfBudget.stream()
                        .filter(student -> !student.isInGroup())
                        .findFirst()
                        .ifPresent(student -> group[finalJ] = student.setInGroup(true));

                if (group[j] == null) {
                    listOfContract.stream()
                            .filter(student -> !student.isInGroup())
                            .findFirst()
                            .ifPresent(student -> {
                                student.setPriority("Budget");
                                group[finalJ] = student.setInGroup(true);
                            });
                }

                System.out.println("loop: " + group[j].getFirstName() + " " + group[j].getLastName());
            }

            for (int j = (int) (group.length * percentOfBudget); j < group.length; j++) {
                int finalJ = j;
                listOfContract.stream()
                        .filter(student -> !student.isInGroup())
                        .findFirst()
                        .ifPresent(student -> group[finalJ] = student.setInGroup(true));

                if (group[j] == null) {
                    listOfBudget.stream()
                            .filter(student -> !student.isInGroup())
                            .findFirst()
                            .ifPresent(student -> {
                                student.setPriority("Contract");
                                group[finalJ] = student.setInGroup(true);
                            });
                }
            }

            groups.add(Arrays.stream(group).sorted((o1, o2) -> {
                if (o2.getPoints() - o1.getPoints() > 0)
                    return 1;
                else if (o2.getPoints() == o1.getPoints())
                    return 0;
                else
                    return -1;
            }).collect(Collectors.toList()));
        }
        return "redirect:/groupView";
    }
}
