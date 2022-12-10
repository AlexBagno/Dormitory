package com.example.dormitory.DAO;

import com.example.dormitory.models.Priority;
import com.example.dormitory.models.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDAO {
    private static int STUDENT_ID;
    private List<Student> students;

    {
        students = new ArrayList<>();

        students.add(new Student(STUDENT_ID++, "Тарас", "Шевченко", 197.5, Priority.BUDGET));
        students.add(new Student(STUDENT_ID++, "Михайло", "Подоляк", 170.7, Priority.CONTRACT));
        students.add(new Student(STUDENT_ID++, "Микита", "Люблян", 192.1, Priority.BUDGET));
        students.add(new Student(STUDENT_ID++, "Денис", "Вілятин", 195, Priority.BUDGET));
        students.add(new Student(STUDENT_ID++, "Олексій", "Рітчин", 164.6, Priority.CONTRACT));
        students.add(new Student(STUDENT_ID++, "Артем", "Деригун", 176, Priority.CONTRACT));
    }

    public List<Student> index() {
        return students;
    }
}
