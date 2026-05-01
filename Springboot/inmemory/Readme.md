# Spring Boot Student REST API with In-Memory Authentication & Authorization (FULL PROJECT)

---

## COMPLETE PROJECT STRUCTURE

```
sb-student-app
│
├── pom.xml
└── src
    └── main
        ├── java
        │   └── org
        │       └── example
        │           ├── MyApplication.java
        │           ├── entity
        │           │   └── Student.java
        │           ├── repository
        │           │   └── StudentRepository.java
        │           ├── service
        │           │   ├── StudentService.java
        │           │   └── StudentServiceImpl.java
        │           ├── restcontroller
        │           │   └── StudentController.java
        │           ├── exception
        │           │   ├── StudentExceptionHandler.java
        │           │   └── StudentNotFoundException.java
        │           ├── dto
        │           │   └── StudentErrorResponse.java
        │           └── security
        │               └── AppSecurityConfiguration.java
        └── resources
            ├── application.properties
            └── data.sql
```

---

## pom.xml (FULL)

```xml
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
    <version>0.0.1-SNAPSHOT</version>

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
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
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

## application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=rootpassword
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
server.port=8080
```

---

## data.sql

```sql
INSERT INTO student VALUES (1,'John','john@gmail.com',5,'Ahmedabad','Male','B.Tech.-IT');
INSERT INTO student VALUES (2,'Jenifer','jenifer@gmail.com',6,'Vadodara','Female','B.Tech.-IT');
INSERT INTO student VALUES (3,'Willkumar','will@gmail.com',8,'Valsad','Male','B.Tech.-IT');
```

---

## MyApplication.java

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

## Student.java (FULL)

```java
package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    private int id;
    private String name;
    private String email;
    private int semester;
    private String city;
    private String gender;
    private String program;

    public Student() {}

    public Student(int id, String name, String email, int semester, String city, String gender, String program) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.semester = semester;
        this.city = city;
        this.gender = gender;
        this.program = program;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
}
```

---

## StudentRepository.java

```java
package org.example.repository;

import org.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {}
```

---

## StudentService.java

```java
package org.example.service;

import org.example.entity.Student;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(int id);
    Student saveStudent(Student student);
    Student updateStudent(int id, Student student);
    String deleteStudent(int id);
}
```

---

## StudentServiceImpl.java (FULL)

```java
package org.example.service;

import org.example.entity.Student;
import org.example.exception.StudentNotFoundException;
import org.example.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    public Student getStudentById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found: " + id));
    }

    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    public Student updateStudent(int id, Student student) {
        Student s = getStudentById(id);
        s.setName(student.getName());
        s.setEmail(student.getEmail());
        s.setSemester(student.getSemester());
        s.setCity(student.getCity());
        s.setGender(student.getGender());
        s.setProgram(student.getProgram());
        return repo.save(s);
    }

    public String deleteStudent(int id) {
        Student s = getStudentById(id);
        repo.delete(s);
        return "Student with id " + id + " deleted";
    }
}
```

---

## StudentController.java (FULL)

```java
package org.example.restcontroller;

import org.example.entity.Student;
import org.example.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return service.getStudentById(id);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return service.saveStudent(student);
    }

    @PatchMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student student) {
        return service.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        return service.deleteStudent(id);
    }
}
```

---

## StudentNotFoundException.java

```java
package org.example.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String msg) {
        super(msg);
    }
}
```

---

## StudentErrorResponse.java

```java
package org.example.dto;

public class StudentErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    public StudentErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}
```

---

## StudentExceptionHandler.java

```java
package org.example.exception;

import org.example.dto.StudentErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleNotFound(StudentNotFoundException ex) {
        return new ResponseEntity<>(
                new StudentErrorResponse(404, ex.getMessage(), System.currentTimeMillis()),
                HttpStatus.NOT_FOUND
        );
    }
}
```

---

## AppSecurityConfiguration.java (FULL)

```java
package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppSecurityConfiguration {

    @Bean
    public UserDetailsManager userDetailsManager() {

        UserDetails s = User.builder().username("sylvester").password("{noop}pass123").roles("STUDENT").build();
        UserDetails t = User.builder().username("tom").password("{noop}pass123").roles("TEACHER").build();
        UserDetails a = User.builder().username("arnold").password("{noop}pass123").roles("TEACHER","ADMIN").build();

        return new InMemoryUserDetailsManager(s, t, a);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("STUDENT","TEACHER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/students").hasRole("TEACHER")
                .requestMatchers(HttpMethod.PATCH, "/api/students/**").hasRole("TEACHER")
                .requestMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("ADMIN")
        );

        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
```

---

## RUN COMMAND

```
mvn spring-boot:run
```

---

## USERS

```
sylvester / pass123 (STUDENT)
tom / pass123 (TEACHER)
arnold / pass123 (ADMIN)
```
