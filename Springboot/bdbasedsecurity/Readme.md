# Spring Security Database Based Authentication - All Codes

This README contains complete code for **Database Based Authentication in Spring Security** using MySQL database, JDBC authentication, Basic Auth, BCrypt password encoding, and role-based authorization.

---

## 1. Project Structure

```text
spring-security-db-auth/
│
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── org/
        │       └── example/
        │           ├── MyApplication.java
        │           ├── controller/
        │           │   └── StudentController.java
        │           ├── entity/
        │           │   └── Student.java
        │           ├── repository/
        │           │   └── StudentRepository.java
        │           ├── security/
        │           │   └── AppSecurityConfiguration.java
        │           └── utils/
        │               └── PasswordEncoder.java
        │
        └── resources/
            ├── application.properties
            └── data.sql
```

---

## 2. Database Tables Used

We are using custom tables:

```text
members  -> instead of users
roles    -> instead of authorities
```

### Users

| Username | Password | Role |
|---|---|---|
| sylvester | pass123 | ROLE_STUDENT |
| tom | pass123 | ROLE_TEACHER |
| arnold | pass123 | ROLE_TEACHER, ROLE_ADMIN |

---

## 3. Create Database

```sql
CREATE DATABASE IF NOT EXISTS test;
USE test;
```

---

## 4. SQL Code for Tables and Data

### Option A: Use BCrypt Password

Password for all users is:

```text
pass123
```

```sql
CREATE TABLE IF NOT EXISTS members (
    membername VARCHAR(50) NOT NULL PRIMARY KEY,
    passwd VARCHAR(68) NOT NULL,
    active TINYINT NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    membername VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    UNIQUE INDEX ix_auth_membername (membername, role),
    CONSTRAINT fk_authorities_members
        FOREIGN KEY (membername)
        REFERENCES members(membername)
);

INSERT INTO members VALUES
('sylvester', '{bcrypt}$2a$12$0Hk9c0JhKpccjikRS7R1GepUKwIUQHQdiuN84JTbhRnrdfjop11H6', 1),
('tom', '{bcrypt}$2a$12$0Hk9c0JhKpccjikRS7R1GepUKwIUQHQdiuN84JTbhRnrdfjop11H6', 1),
('arnold', '{bcrypt}$2a$12$0Hk9c0JhKpccjikRS7R1GepUKwIUQHQdiuN84JTbhRnrdfjop11H6', 1);

INSERT INTO roles VALUES
('sylvester', 'ROLE_STUDENT'),
('tom', 'ROLE_TEACHER'),
('arnold', 'ROLE_TEACHER'),
('arnold', 'ROLE_ADMIN');
```

### Option B: Quick Testing with No Encryption

If BCrypt gives `401 Unauthorized`, use this for testing:

```sql
UPDATE members
SET passwd = '{noop}pass123'
WHERE membername IN ('sylvester', 'tom', 'arnold');
```

---

## 5. `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>spring-security-db-auth</artifactId>
    <version>1.0.0</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.1</version>
        <relativePath/>
    </parent>

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
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
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

## 6. `src/main/resources/application.properties`

```properties
spring.application.name=spring-security-db-auth

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=rootpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.sql.init.mode=never
```

> Change `rootpassword` according to your MySQL password.

---

## 7. `src/main/java/org/example/MyApplication.java`

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

## 8. `src/main/java/org/example/entity/Student.java`

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

## 9. `src/main/java/org/example/repository/StudentRepository.java`

```java
package org.example.repository;

import org.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
```

---

## 10. `src/main/java/org/example/controller/StudentController.java`

```java
package org.example.controller;

import org.example.entity.Student;
import org.example.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PatchMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setSemester(updatedStudent.getSemester());
        student.setCity(updatedStudent.getCity());
        student.setGender(updatedStudent.getGender());
        student.setProgram(updatedStudent.getProgram());

        return studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }
}
```

---

## 11. `src/main/java/org/example/security/AppSecurityConfiguration.java`

```java
package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class AppSecurityConfiguration {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager userDetailsManager =
                new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery(
                "select membername, passwd, active from members where membername=?"
        );

        userDetailsManager.setAuthoritiesByUsernameQuery(
                "select membername, role from roles where membername=?"
        );

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/students")
                        .hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/students/**")
                        .hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/students")
                        .hasRole("TEACHER")

                        .requestMatchers(HttpMethod.PATCH, "/api/students/**")
                        .hasRole("TEACHER")

                        .requestMatchers(HttpMethod.DELETE, "/api/students/**")
                        .hasRole("ADMIN")
        );

        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
```

---

## 12. `src/main/java/org/example/utils/PasswordEncoder.java`

```java
package org.example.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static void main(String[] args) {

        String plainPassword = "pass123";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashedPassword = passwordEncoder.encode(plainPassword);

        System.out.println("Original Password: " + plainPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        boolean isPasswordCorrect =
                passwordEncoder.matches("pass123", hashedPassword);

        System.out.println("Password Verification: " + isPasswordCorrect);
    }
}
```

---

## 13. Insert Sample Students

Run this SQL after application creates the `student` table:

```sql
INSERT INTO student (name, email, semester, city, gender, program) VALUES
('Jenifer', 'jenifer@gmail.com', 6, 'Vadodara', 'Female', 'B.Tech - IT'),
('Willkumar', 'will@gmail.com', 6, 'Valsad', 'Male', 'B.Tech - IT'),
('Keanukumar', 'keanu@gmail.com', 6, 'Karamsad', 'Male', 'B.Tech - IT');
```

---

## 14. Run Project

```bash
mvn spring-boot:run
```

Expected output:

```text
Tomcat started on port 8080
Started MyApplication in 4 seconds
```

---

## 15. Postman Testing

### Test 1: GET Students

```text
Method: GET
URL: http://localhost:8080/api/students
Authorization: Basic Auth
Username: arnold
Password: pass123
```

Expected output:

```text
Status: 200 OK
```

Example response:

```json
[
  {
    "id": 1,
    "name": "Jenifer",
    "email": "jenifer@gmail.com",
    "semester": 6,
    "city": "Vadodara",
    "gender": "Female",
    "program": "B.Tech - IT"
  },
  {
    "id": 2,
    "name": "Willkumar",
    "email": "will@gmail.com",
    "semester": 6,
    "city": "Valsad",
    "gender": "Male",
    "program": "B.Tech - IT"
  },
  {
    "id": 3,
    "name": "Keanukumar",
    "email": "keanu@gmail.com",
    "semester": 6,
    "city": "Karamsad",
    "gender": "Male",
    "program": "B.Tech - IT"
  }
]
```

---

### Test 2: DELETE Student with Teacher User

```text
Method: DELETE
URL: http://localhost:8080/api/students/1
Authorization: Basic Auth
Username: tom
Password: pass123
```

Expected output:

```text
Status: 403 Forbidden
```

Reason:

```text
tom has ROLE_TEACHER, but DELETE requires ROLE_ADMIN.
```

---

### Test 3: DELETE Student with Admin User

```text
Method: DELETE
URL: http://localhost:8080/api/students/1
Authorization: Basic Auth
Username: arnold
Password: pass123
```

Expected output:

```text
Status: 200 OK
```

Response:

```text
Student deleted successfully
```

---

## 16. Role Permissions

| API | Allowed Role |
|---|---|
| GET `/api/students` | STUDENT, TEACHER, ADMIN |
| GET `/api/students/{id}` | STUDENT, TEACHER, ADMIN |
| POST `/api/students` | TEACHER |
| PATCH `/api/students/{id}` | TEACHER |
| DELETE `/api/students/{id}` | ADMIN |

---

## 17. Fix for 401 Unauthorized

If you get:

```text
Status: 401 Unauthorized
```

Check these points:

1. Username exists in `members` table.
2. Password starts with `{bcrypt}` or `{noop}`.
3. Password is correct.
4. `active` value is `1`.
5. Role exists in `roles` table.
6. Role name starts with `ROLE_`.
7. Custom queries in `AppSecurityConfiguration.java` are correct.

Quick fix for testing:

```sql
UPDATE members
SET passwd = '{noop}pass123'
WHERE membername IN ('sylvester', 'tom', 'arnold');
```

Then try Postman again with:

```text
Username: arnold
Password: pass123
```

---

## 18. Fix for 403 Forbidden

If you get:

```text
Status: 403 Forbidden
```

It means:

```text
Authentication successful, but authorization failed.
```

Example:

```text
tom can login, but cannot DELETE because tom is ROLE_TEACHER.
DELETE requires ROLE_ADMIN.
```

Use:

```text
Username: arnold
Password: pass123
```

for DELETE request.

---

## 19. Important Notes

- `401 Unauthorized` means wrong username/password or DB authentication issue.
- `403 Forbidden` means login is correct, but role is not allowed.
- Spring Security needs roles stored as `ROLE_ADMIN`, `ROLE_TEACHER`, etc.
- In Java config, use `.hasRole("ADMIN")`, not `.hasRole("ROLE_ADMIN")`.
- For learning, `{noop}` is okay.
- For real projects, use `{bcrypt}`.

---

## 20. Complete Testing Credentials

```text
Username: sylvester
Password: pass123
Role: ROLE_STUDENT
Allowed: GET only
```

```text
Username: tom
Password: pass123
Role: ROLE_TEACHER
Allowed: GET, POST, PATCH
```

```text
Username: arnold
Password: pass123
Role: ROLE_TEACHER, ROLE_ADMIN
Allowed: GET, POST, PATCH, DELETE
```
