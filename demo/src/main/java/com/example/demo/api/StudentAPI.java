package com.example.demo.api;

import com.example.demo.model.Student;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// CRUD Student
@RestController
@RequestMapping("/api/student")
public class StudentAPI {

    List<Student> students = new ArrayList<>();

    // Create
    // POST
    @PostMapping
    public ResponseEntity createStudent(@Valid @RequestBody Student student) {
        students.add(student);
        return ResponseEntity.ok(student);
    }

    // GET list student
    @GetMapping
    public ResponseEntity getAllStudent() {
        return ResponseEntity.ok(students);
    }

    // Get 1 student
    @GetMapping("student/:id")
    public void getStudentById() {

    }

    // Update
    @PutMapping("student/:id")
    public void updateStudent() {

    }

    // Delete
    @DeleteMapping("/student/:id")
    public void deleteStudent() {

    }
}
