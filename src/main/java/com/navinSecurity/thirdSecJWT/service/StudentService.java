package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.Student;
import com.navinSecurity.thirdSecJWT.model.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepo service;
    List<Student> students=new ArrayList<>();



    public List<Student> getStudents() {
        return service.findAll();
    }

    public Student postStudent(Student stud) {
        System.out.println("SERVICE: " + stud.toString());
        service.save(stud);
        return stud;
    }
}
