package org.example.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BTechStudent implements Student {

    private Semester studentSemester;

    public BTechStudent() {
        System.out.println("Constructor BTechStudent() called");
    }

    @Autowired
    public void setStudentSemester(@Qualifier("BTechSemester") Semester studentSemester) {
        System.out.println("setStudentSemester() called");
        this.studentSemester = studentSemester;
    }

    @PostConstruct
    public void doInitialization() {
        System.out.println("BTechStudent.doInitialization()");
    }

    @PreDestroy
    public void doCleanup() {
        System.out.println("BTechStudent.doCleanup()");
    }

    @Override
    public String getCurrentStatus() {
        return "is studying the following subjects: "
                + studentSemester.getSubjects()
                + " in semester "
                + studentSemester.getSemesterNo();
    }
}