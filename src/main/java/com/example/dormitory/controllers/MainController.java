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
    private List<Student> leftStudents = new ArrayList<>();

    @Autowired
    public MainController(StudentDAO studentDAO, AdminDAO adminDAO) {
        this.studentDAO = studentDAO;
        this.adminDAO = adminDAO;
    }

    @GetMapping()
    public String chosePage(Model model) {
        return "index";
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
                .sorted(Comparator.comparingDouble(Student::getPoints))
                .collect(Collectors.toList()));

        percentOfBudget = percentOfBudget / 100;
        System.out.println("percentOfBudget = " + percentOfBudget);

        int studentsPerGroup = listToWork.size() / amountGroups;
        int remainingStudents = listToWork.size() % amountGroups;

        for (int i = 1; i <= amountGroups; i++) {
            int finalI = i;
            int extra = i <= remainingStudents ? 1 : 0;
            System.out.println("group " + i + " contains " + (studentsPerGroup + extra) + " students.");

            Student[] group = new Student[studentsPerGroup + extra];

            for (int j = 0; j < (int) (group.length * percentOfBudget); j++) {
                int finalJ = j;

                listOfBudget.stream()
                        .filter(student -> !student.isInGroup())
                        .findAny()
                        .ifPresent(student -> group[finalJ] = student.setInGroup(true));

                if (group[j] == null) {
                    listOfContract.stream()
                            .sorted(Comparator.comparingDouble(Student::getPoints).reversed())
                            .filter(student -> !student.isInGroup())
                            .findAny()
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
                        .findAny()
                        .ifPresent(student -> group[finalJ] = student.setInGroup(true));

                if (group[j] == null) {
                    listOfBudget.stream()
                            .filter(student -> !student.isInGroup())
                            .findAny()
                            .ifPresent(student -> {
                                student.setPriority("Contract");
                                group[finalJ] = student.setInGroup(true);
                            });
                }
            }

            groups.add(Arrays.stream(group)
                    .sorted(Comparator.comparingDouble(Student::getPoints)
                            .reversed())
                    .collect(Collectors.toList()));
        }
        return "redirect:/groupView";
    }

    @GetMapping("/groupView")
    public String userView(Model model) {
        if (groups.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("students", groups.stream()
                .map(students -> students.stream()
                        .sorted(Comparator.comparingDouble(Student::getPoints)
                                .reversed()))
                .collect(Collectors.toList()));

        model.addAttribute("leftStudents", leftStudents.stream()
                .sorted(Comparator.comparingDouble(Student::getPoints)
                        .reversed())
                .collect(Collectors.toList()));
        return "pages/adminView";
    }

    @RequestMapping(value = "/groupView", method = RequestMethod.POST)
    public String deleteStudent(@ModelAttribute(value = "studentId") int id) {
        if (leftStudents.stream().noneMatch(student -> student.getId() == id)) {
            for (List<Student> students : groups) {
                if (students != null)
                    for (Student s : students) {
                        System.out.println("for " + s.getLastName() + " " + s.getFirstName());
                        if (s != null && s.getId() == id) {
                            leftStudents.add(s);
                            students.remove(s);
                            return "redirect:/groupView";
                        }
                    }
            }
        } else {
            for (Student s : leftStudents) {
                if (s.getId() == id) {
                    groups.stream().min(Comparator.comparingInt(List::size)).ifPresent(students -> students.add(s));
                    leftStudents.remove(s);
                    return "redirect:/groupView";
                }
            }
        }
        return "redirect:/groupView";
    }

    @GetMapping("/groupViewUser")
    public String groupViewUser(Model model) {
        if (groups.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("students", groups.stream()
                .map(students -> students.stream()
                        .sorted(Comparator.comparingDouble(Student::getPoints)
                                .reversed()))
                .collect(Collectors.toList()));

        return "view/groupView";
    }
}
