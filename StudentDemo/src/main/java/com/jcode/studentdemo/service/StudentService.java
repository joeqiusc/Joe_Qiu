package com.jcode.studentdemo.service;

import com.jcode.studentdemo.model.Student;

import java.util.List;


public interface StudentService {

    public Student saveStudent(Student student);

    public List<Student> getAll(Student student);
}
