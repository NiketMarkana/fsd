# Spring Boot REST API - After Error Handling

This README contains the complete setup process and all code for the Student REST API after adding exception handling.

This version includes:

- Custom error response DTO
- Custom exception class
- Global exception handler using `@ControllerAdvice`
- Correct HTTP status code using `ResponseEntity`

---

## 1. Project Structure

```text
sb-student-app
│── pom.xml
└── src
    └── main
        └── java
            └── org
                └── example
                    ├── MyApplication.java
                    ├── dto
                    │   └── StudentErrorResponse.java
                    ├── entity
                    │   └── Student.java
                    ├── exception
                    │   ├── StudentNotFoundException.java
                    │   └── StudentExceptionHandler.java
                    └── restcontroller
                        └── StudentController.java
```

---

## 2. Required Software

- JDK 17 or above
- Maven
- IntelliJ IDEA Community Edition / Ultimate Edition
- Postman or browser for testing API

---

## 3. Create Spring Boot Project

### Using Spring Initializr

1. Open browser.
2. Go to `https://start.spring.io`.
3. Select:
    - Project: Maven
    - Language: Java
    - Spring Boot: 3.2.1 or latest stable version
    - Group: `org.example`
    - Artifact: `sb-student-app`
    - Packaging: Jar
    - Java: 17
4. Add dependency:
    - Spring Web
5. Click Generate.
6. Extract ZIP file.
7. Open project in IntelliJ IDEA.

---

## 4. pom.xml

File: `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.1</version>
    </parent>

    <groupId>org.example</groupId>
    <artifactId>sb-student-app</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

---

## 5. Main Class

File: `src/main/java/org/example/MyApplication.java`

```java
package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

---

## 6. Student Entity Class

Create package:

```text
org.example.entity
```

File: `src/main/java/org/example/entity/Student.java`

```java
package org.example.entity;

public class Student {

    private int id;
    private String name;
    private String email;
    private int semester;
    private String city;
    private String gender;
    private String program;

    public Student() {
    }

    public Student(int id, String name, String email, int semester,
                   String city, String gender, String program) {
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
```

---

## 7. Student Error Response DTO

Create package:

```text
org.example.dto
```

File: `src/main/java/org/example/dto/StudentErrorResponse.java`

```java
package org.example.dto;

public class StudentErrorResponse {

    private int status;
    private String message;
    private long timestamp;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
```

---

## 8. Custom Exception Class

Create package:

```text
org.example.exception
```

File: `src/main/java/org/example/exception/StudentNotFoundException.java`

```java
package org.example.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String message) {
        super(message);
    }
}
```

---

## 9. Global Exception Handler

File: `src/main/java/org/example/exception/StudentExceptionHandler.java`

```java
package org.example.exception;

import org.example.dto.StudentErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(
            StudentNotFoundException exception) {

        StudentErrorResponse errorResponse = new StudentErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exception) {

        StudentErrorResponse errorResponse = new StudentErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Invalid request");
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
```

---

## 10. Student REST Controller After Error Handling

Create package:

```text
org.example.restcontroller
```

File: `src/main/java/org/example/restcontroller/StudentController.java`

```java
package org.example.restcontroller;

import jakarta.annotation.PostConstruct;
import org.example.entity.Student;
import org.example.exception.StudentNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private List<Student> allStudents;

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

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return allStudents;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable int studentId) {
        Student matchingStudent = allStudents.stream()
                .filter(student -> student.getId() == studentId)
                .findFirst()
                .orElse(null);

        if (matchingStudent == null) {
            throw new StudentNotFoundException("Student not found with id " + studentId);
        }

        return matchingStudent;
    }

    @PostMapping("/students")
    public Student addNewStudent(@RequestBody Student student) {
        if (student.getId() == 0) {
            student.setId(allStudents.size() + 1);
        }

        allStudents.add(student);
        return student;
    }

    @PatchMapping("/students/{studentId}")
    public Student updateStudent(@RequestBody Student updateStudent,
                                 @PathVariable int studentId) {

        Student matchingStudent = allStudents.stream()
                .filter(student -> student.getId() == studentId)
                .findFirst()
                .orElse(null);

        if (matchingStudent == null) {
            throw new StudentNotFoundException("Student not found with id " + studentId);
        }

        if (updateStudent.getName() != null) {
            matchingStudent.setName(updateStudent.getName());
        }

        if (updateStudent.getCity() != null) {
            matchingStudent.setCity(updateStudent.getCity());
        }

        if (updateStudent.getEmail() != null) {
            matchingStudent.setEmail(updateStudent.getEmail());
        }

        if (updateStudent.getGender() != null) {
            matchingStudent.setGender(updateStudent.getGender());
        }

        if (updateStudent.getSemester() != 0) {
            matchingStudent.setSemester(updateStudent.getSemester());
        }

        if (updateStudent.getProgram() != null) {
            matchingStudent.setProgram(updateStudent.getProgram());
        }

        return matchingStudent;
    }

    @DeleteMapping("/students/{studentId}")
    public String deleteStudentById(@PathVariable int studentId) {
        Student matchingStudent = allStudents.stream()
                .filter(student -> student.getId() == studentId)
                .findFirst()
                .orElse(null);

        if (matchingStudent == null) {
            throw new StudentNotFoundException("Student not found with id " + studentId);
        }

        allStudents.remove(matchingStudent);
        return "Student with id=" + studentId + " removed successfully.";
    }
}
```

---

## 11. Run Application

```bash
mvn spring-boot:run
```

Or run `MyApplication.java` directly from IntelliJ IDEA.

---

## 12. API Testing

### GET All Students

```text
GET http://localhost:8080/api/students
```

### GET Student By Valid ID

```text
GET http://localhost:8080/api/students/1
```

Expected output:

```json
{
  "id": 1,
  "name": "Tom",
  "email": "tom@gmail.com",
  "semester": 6,
  "city": "Ahmedabad",
  "gender": "Male",
  "program": "B.Tech-IT"
}
```

### GET Student By Invalid ID

```text
GET http://localhost:8080/api/students/6
```

Expected output:

```json
{
  "status": 404,
  "message": "Student not found with id 6",
  "timestamp": 1710000000000
}
```

HTTP status:

```text
404 Not Found
```

### GET Student With Invalid ID Type

```text
GET http://localhost:8080/api/students/Tom
```

Expected output:

```json
{
  "status": 400,
  "message": "Invalid request",
  "timestamp": 1710000000000
}
```

HTTP status:

```text
400 Bad Request
```

### POST Add Student

```text
POST http://localhost:8080/api/students
```

Body:

```json
{
  "name": "Arnold",
  "email": "arnold@gmail.com",
  "semester": 6,
  "city": "Surat",
  "gender": "Male",
  "program": "B.Tech-IT"
}
```

Expected output:

```json
{
  "id": 4,
  "name": "Arnold",
  "email": "arnold@gmail.com",
  "semester": 6,
  "city": "Surat",
  "gender": "Male",
  "program": "B.Tech-IT"
}
```

### PATCH Update Student

```text
PATCH http://localhost:8080/api/students/4
```

Body:

```json
{
  "city": "Nadiad"
}
```

### DELETE Student

```text
DELETE http://localhost:8080/api/students/4
```

Expected output:

```text
Student with id=4 removed successfully.
```

---

## 13. Important Concepts

### Custom Error Response Class

`StudentErrorResponse` is used to return error details as JSON.

It contains:

```text
status
message
timestamp
```

### Custom Exception Class

`StudentNotFoundException` is used internally when student data is not found.

### @ExceptionHandler

`@ExceptionHandler` is used to handle exceptions and return a custom error response.

### ResponseEntity

`ResponseEntity` is used to return:

```text
Response body + HTTP status code
```

### @ControllerAdvice

`@ControllerAdvice` is used for global exception handling.

It handles exceptions thrown from controllers.

---

## 14. Build JAR

```bash
mvn package
```

Run JAR:

```bash
java -jar target/sb-student-app-1.0-SNAPSHOT.jar
```
