// src/main/java/org/example/entities/Student.java

package org.example.entities;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "STUDENT_ID")
    private int studentId;

    @Column(name = "STUDENT_NAME")
    private String studentName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COLLEGE_FK")
    private StudentCollege studentCollege;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public StudentCollege getStudentCollege() {
        return studentCollege;
    }

    public void setStudentCollege(StudentCollege studentCollege) {
        this.studentCollege = studentCollege;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}