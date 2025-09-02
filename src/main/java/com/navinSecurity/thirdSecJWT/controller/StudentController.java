package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.model.Student;
import com.navinSecurity.thirdSecJWT.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api")
@CrossOrigin
public class StudentController {

        @Autowired
        StudentService service;

        @GetMapping("/csrf-token")
        public CsrfToken getCsrfToken(HttpServletRequest request){
            return (CsrfToken) request.getAttribute("_csrf");
        }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents(){
        return new ResponseEntity<List<Student>>(service.getStudents(),HttpStatus.OK);
    }
    @PostMapping("/student/post")
    public ResponseEntity<Student> postStudent(@RequestBody Student stud){

        return new ResponseEntity<>(service.postStudent(stud),HttpStatus.OK) ;
    }
}
