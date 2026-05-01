# Spring Data JPA Student REST API

This project is a Spring Boot REST API using Spring Data JPA and MySQL. It follows layered architecture:

```text
Student REST Controller  ->  Student Service  ->  Student Repository  ->  MySQL Database
```

---

## Project Structure

```text
sb-student-app
│
├── pom.xml
└── src
    └── main
        ├── java
        │   └── org
        │       └── example
        │           ├── MyApplication.java
        │           ├── dto
        │           │   └── StudentErrorResponse.java
        │           ├── entity
        │           │   └── Student.java
        │           ├── exception
        │           │   ├── StudentExceptionHandler.java
        │           │   └── StudentNotFoundException.java
        │           ├── repository
        │           │   └── StudentRepository.java
        │           ├── restcontroller
        │           │   └── StudentController.java
        │           └── service
        │               ├── StudentService.java
        │               └── StudentServiceImpl.java
        │
        └── resources
            └── application.properties
```

---

## 1. pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.1</version>
        <relativePath/>
    </parent>

    <groupId>org.example</groupId>
    <artifactId>sb-student-app</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>sb-student-app</name>
    <description>Spring Data JPA Student REST API</description>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.2.0</version>
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

## 2. application.properties

File path:

```text
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=rootpassword

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

After table creation, change this line:

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

## 3. MyApplication.java

File path:

```text
src/main/java/org/example/MyApplication.java
```

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

## 4. Student.java

File path:

```text
src/main/java/org/example/entity/Student.java
```

```java
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

## 5. StudentRepository.java

File path:

```text
src/main/java/org/example/repository/StudentRepository.java
```

```java
package org.example.repository;

import org.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
```

---

## 6. StudentService.java

File path:

```text
src/main/java/org/example/service/StudentService.java
```

```java
package org.example.service;

import org.example.entity.Student;

import java.util.List;

public interface StudentService {

    public List<Student> findAll();

    public Student findById(int studentId);

    public Student save(Student student);

    public Student update(int studentId, Student student);

    public void deleteById(int studentId);
}
```

---

## 7. StudentServiceImpl.java

File path:

```text
src/main/java/org/example/service/StudentServiceImpl.java
```

```java
package org.example.service;

import jakarta.transaction.Transactional;
import org.example.entity.Student;
import org.example.exception.StudentNotFoundException;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(int studentId) {

        Optional<Student> foundStudent = studentRepository.findById(studentId);

        Student student = null;

        if (foundStudent.isPresent()) {
            student = foundStudent.get();
        } else {
            throw new StudentNotFoundException("Student with id " + studentId + " not found");
        }

        return student;
    }

    @Transactional
    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public Student update(int studentId, Student updateStudent) {

        Student matchingStudent = findById(studentId);

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

        return studentRepository.save(matchingStudent);
    }

    @Transactional
    @Override
    public void deleteById(int studentId) {

        Student matchingStudent = findById(studentId);

        studentRepository.deleteById(studentId);
    }
}
```

---

## 8. StudentController.java

File path:

```text
src/main/java/org/example/restcontroller/StudentController.java
```

```java
package org.example.restcontroller;

import org.example.entity.Student;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable int studentId) {
        return studentService.findById(studentId);
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

    @PatchMapping("/students/{studentId}")
    public Student updateStudent(@RequestBody Student student, @PathVariable int studentId) {
        return studentService.update(studentId, student);
    }

    @DeleteMapping("/students/{studentId}")
    public String deleteStudentById(@PathVariable int studentId) {
        studentService.deleteById(studentId);
        return "Student with id " + studentId + " was deleted successfully";
    }
}
```

---

## 9. StudentErrorResponse.java

File path:

```text
src/main/java/org/example/dto/StudentErrorResponse.java
```

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

## 10. StudentNotFoundException.java

File path:

```text
src/main/java/org/example/exception/StudentNotFoundException.java
```

```java
package org.example.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String message) {
        super(message);
    }
}
```

---

## 11. StudentExceptionHandler.java

File path:

```text
src/main/java/org/example/exception/StudentExceptionHandler.java
```

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
            StudentNotFoundException studentNotFoundException) {

        StudentErrorResponse errorResponse = new StudentErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(studentNotFoundException.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exception) {

        StudentErrorResponse errorResponse = new StudentErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
```

---

## MySQL Database Setup

Create database:

```sql
CREATE DATABASE test;
USE test;
```

Insert records after running application:

```sql
INSERT INTO test.student
(semester, city, email, gender, name, program)
VALUES
(6, 'Ahmedabad', 'tom@gmail.com', 'Male', 'Tom', 'B.Tech.-IT');

INSERT INTO test.student
(semester, city, email, gender, name, program)
VALUES
(6, 'Vadodara', 'jenifer@gmail.com', 'Female', 'Jenifer', 'B.Tech.-IT');

INSERT INTO test.student
(semester, city, email, gender, name, program)
VALUES
(6, 'Vadodara', 'will@gmail.com', 'Male', 'Will', 'B.Tech.-IT');
```

Check records:

```sql
SELECT * FROM test.student;
```

---

## Run Application

Using Maven:

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

---

## API Testing and Output

### 1. GET All Students

```text
GET http://localhost:8080/api/students
```

Output:

```json
[
  {
    "id": 1,
    "name": "Tom",
    "email": "tom@gmail.com",
    "semester": 6,
    "city": "Ahmedabad",
    "gender": "Male",
    "program": "B.Tech.-IT"
  },
  {
    "id": 2,
    "name": "Jenifer",
    "email": "jenifer@gmail.com",
    "semester": 6,
    "city": "Vadodara",
    "gender": "Female",
    "program": "B.Tech.-IT"
  },
  {
    "id": 3,
    "name": "Will",
    "email": "will@gmail.com",
    "semester": 6,
    "city": "Vadodara",
    "gender": "Male",
    "program": "B.Tech.-IT"
  }
]
```

---

### 2. GET Student By ID

```text
GET http://localhost:8080/api/students/1
```

Output:

```json
{
  "id": 1,
  "name": "Tom",
  "email": "tom@gmail.com",
  "semester": 6,
  "city": "Ahmedabad",
  "gender": "Male",
  "program": "B.Tech.-IT"
}
```

---

### 3. POST Create Student

```text
POST http://localhost:8080/api/students
```

Body:

```json
{
  "name": "Kevin",
  "email": "kevin@gmail.com",
  "semester": 6,
  "city": "Surat",
  "gender": "Male",
  "program": "B.Tech.-IT"
}
```

Output:

```json
{
  "id": 4,
  "name": "Kevin",
  "email": "kevin@gmail.com",
  "semester": 6,
  "city": "Surat",
  "gender": "Male",
  "program": "B.Tech.-IT"
}
```

---

### 4. PATCH Update Student

```text
PATCH http://localhost:8080/api/students/3
```

Body:

```json
{
  "city": "Rajkot",
  "program": "B.Tech.-CE"
}
```

Output:

```json
{
  "id": 3,
  "name": "Will",
  "email": "will@gmail.com",
  "semester": 6,
  "city": "Rajkot",
  "gender": "Male",
  "program": "B.Tech.-CE"
}
```

---

### 5. DELETE Student

```text
DELETE http://localhost:8080/api/students/4
```

Output:

```text
Student with id 4 was deleted successfully
```

---

### 6. Error Output

```text
GET http://localhost:8080/api/students/10
```

Output:

```json
{
  "status": 404,
  "message": "Student with id 10 not found",
  "timestamp": 1705326411030
}
```
