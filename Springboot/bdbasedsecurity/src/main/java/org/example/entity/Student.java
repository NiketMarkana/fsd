package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private int semester;
    private String city;
    private String gender;
    private String program;

    public Student() {
    }

    public Student(String name, String email, int semester, String city, String gender, String program) {
        this.name = name;
        this.email = email;
        this.semester = semester;
        this.city = city;
        this.gender = gender;
        this.program = program;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getSemester() {
        return semester;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }

    public String getProgram() {
        return program;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}