// src/main/java/org/example/entities/StudentCollege.java

package org.example.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "STUDENT_COLLEGE")
public class StudentCollege {

    @Id
    @GeneratedValue
    @Column(name = "COLLEGE_ID")
    private Long collegeId;

    @Column(name = "COLLEGE_NAME")
    private String collegeName;

    @OneToMany(
            mappedBy = "studentCollege",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Student> students;

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "StudentCollege{" +
                "collegeId=" + collegeId +
                ", collegeName='" + collegeName + '\'' +
                ", students=" + students +
                '}';
    }
}