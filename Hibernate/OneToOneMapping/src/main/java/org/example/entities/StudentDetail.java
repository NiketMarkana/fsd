package org.example.entities;

import jakarta.persistence.*;

@Entity
public class StudentDetail {

    @Id
    @GeneratedValue
    @Column(name = "STUDENTDETAIL_ID")
    private int studentDetailId;

    @Column(name = "ZIPCODE")
    private int zipCode;

    // INVERSE SIDE
    @OneToOne(mappedBy = "studentDetail", cascade = CascadeType.ALL)
    private Student student;

    public int getStudentDetailId() {
        return studentDetailId;
    }

    public void setStudentDetailId(int studentDetailId) {
        this.studentDetailId = studentDetailId;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}