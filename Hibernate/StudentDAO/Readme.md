# Hibernate CRUD Operation - Student Management (Full Code Included)

---

## 📌 Project Overview

This project demonstrates **CRUD (Create, Read, Update, Delete)** operations using **Hibernate ORM + MySQL**.

---

## 📁 Project Structure

```
jpademo/
│
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── org/example/
        │       ├── Main.java
        │       ├── dao/
        │       │   ├── StudentDAO.java
        │       │   └── StudentDAOImpl.java
        │       └── entities/
        │           └── Student.java
        └── resources/
            └── hibernate.properties
```

---

## ⚙️ pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>jpademo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>19</maven.compiler.source>
        <maven.compiler.target>19</maven.compiler.target>
    </properties>

    <dependencies>

        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.3.0.Final</version>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.1.0</version>
        </dependency>

        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>5.0.1</version>
        </dependency>

    </dependencies>
</project>
```

---

## ⚙️ hibernate.properties

```properties
hibernate.connection.url=jdbc:mysql://localhost:3306/test
hibernate.connection.driver_class=com.mysql.cj.jdbc.Driver
hibernate.dialect=org.hibernate.dialect.MySQLDialect

hibernate.connection.username=root
hibernate.connection.password=rootpassword

hibernate.show_sql=true
hibernate.format_sql=true
hibernate.hbm2ddl.auto=create
```

---

## 🧩 Student Entity

```java
package org.example.entities;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "student_name")
    private String studentName;

    public Student() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
```

---

## 🧩 StudentDAO Interface

```java
package org.example.dao;

import org.example.entities.Student;
import java.util.List;

public interface StudentDAO {

    void saveStudent(Student student);

    Student getStudentById(long id);

    List<Student> getAllStudents();

    void updateStudent(Student student);

    void deleteStudent(long id);
}
```

---

## 🧩 StudentDAOImpl

```java
package org.example.dao;

import org.example.entities.Student;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private final SessionFactory sessionFactory;

    public StudentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        }
    }

    @Override
    public Student getStudentById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, id);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query =
                    session.createQuery("FROM Student", Student.class);
            return query.list();
        }
    }

    @Override
    public void updateStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            Student existing =
                    session.get(Student.class, student.getId());

            if (existing != null) {
                existing.setStudentName(student.getStudentName());
            }

            tx.commit();
        }
    }

    @Override
    public void deleteStudent(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            Student student =
                    session.get(Student.class, id);

            if (student != null) {
                session.remove(student);
            }

            tx.commit();
        }
    }
}
```

---

## 🧩 Main Class

```java
package org.example;

import org.example.dao.*;
import org.example.entities.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.addAnnotatedClass(Student.class);

        SessionFactory factory =
                config.buildSessionFactory();

        StudentDAO dao = new StudentDAOImpl(factory);

        // CREATE
        Student s1 = new Student();
        s1.setStudentName("Tom Cruise");

        Student s2 = new Student();
        s2.setStudentName("Will Smith");

        System.out.println("Adding: " + s1);
        System.out.println("Adding: " + s2);

        dao.saveStudent(s1);
        dao.saveStudent(s2);

        // READ
        List<Student> students = dao.getAllStudents();
        System.out.println("Students: " + students);

        // UPDATE
        Student update = dao.getStudentById(1);
        if (update != null) {
            update.setStudentName("Tomkumar Cruise");
            dao.updateStudent(update);
        }

        System.out.println("Updated: " + dao.getStudentById(1));

        // DELETE
        dao.deleteStudent(2);
        System.out.println("Deleted student id 2");

        System.out.println("Final Students: " + dao.getAllStudents());

        factory.close();
    }
}
```

---

## 🗄️ Database Setup

```sql
CREATE DATABASE test;
USE test;
```

---

## 🚀 Run

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="org.example.Main"
```

---

## 🧪 Output

```
Adding: Student{id=0, studentName='Tom Cruise'}
Adding: Student{id=0, studentName='Will Smith'}

Students: [Student{id=1, studentName='Tom Cruise'}, Student{id=2, studentName='Will Smith'}]

Updated: Student{id=1, studentName='Tomkumar Cruise'}

Deleted student id 2

Final Students: [Student{id=1, studentName='Tomkumar Cruise'}]
```

---

## ✅ Conclusion

This project clearly demonstrates:

* Hibernate setup
* Entity mapping
* DAO pattern
* CRUD operations

---
