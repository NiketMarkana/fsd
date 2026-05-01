package org.example.restcontroller;

import jakarta.annotation.PostConstruct;
import org.example.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private List<Student> allStudents;

    // Load initial data
    @PostConstruct
    public void loadStudents() {
        allStudents = new ArrayList<>();

        allStudents.add(new Student(1, "Tom", "tom@gmail.com", 6,
                "Ahmedabad", "Male", "B.Tech-IT"));

        allStudents.add(new Student(2, "Will", "will@gmail.com", 6,
                "Vadodara", "Male", "B.Tech-IT"));

        allStudents.add(new Student(3, "Jenifer", "jenifer@gmail.com", 6,
                "Vadodara", "Female", "B.Tech-IT"));
    }

    // ✅ GET ALL
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return allStudents;
    }

    // ✅ GET BY ID
    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable int studentId) {
        return allStudents.stream()
                .filter(s -> s.getId() == studentId)
                .findFirst()
                .orElse(null);
    }

    // ✅ POST (CREATE)
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        if (student.getId() == 0) {
            student.setId(allStudents.size() + 1);
        }
        allStudents.add(student);
        return student;
    }

    // ✅ PATCH (UPDATE PARTIAL)
    @PatchMapping("/students/{studentId}")
    public Student updateStudent(@RequestBody Student updateStudent,
                                 @PathVariable int studentId) {

        Student student = allStudents.stream()
                .filter(s -> s.getId() == studentId)
                .findFirst()
                .orElse(null);

        if (student == null) return null;

        if (updateStudent.getName() != null)
            student.setName(updateStudent.getName());

        if (updateStudent.getEmail() != null)
            student.setEmail(updateStudent.getEmail());

        if (updateStudent.getCity() != null)
            student.setCity(updateStudent.getCity());

        if (updateStudent.getProgram() != null)
            student.setProgram(updateStudent.getProgram());

        if (updateStudent.getGender() != null)
            student.setGender(updateStudent.getGender());

        if (updateStudent.getSemester() != 0)
            student.setSemester(updateStudent.getSemester());

        return student;
    }

    // ✅ DELETE
    @DeleteMapping("/students/{studentId}")
    public String deleteStudent(@PathVariable int studentId) {

        Student student = allStudents.stream()
                .filter(s -> s.getId() == studentId)
                .findFirst()
                .orElse(null);

        if (student != null) {
            allStudents.remove(student);
            return "Student deleted successfully";
        }

        return "Student not found";
    }
}