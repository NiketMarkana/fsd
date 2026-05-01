# Hibernate Many-to-One Bidirectional Mapping Example

## 📌 Project Overview

This project demonstrates **Hibernate Many-to-One Bidirectional Mapping** between:

* `Student` (Many side)
* `StudentCollege` (One side)

👉 One College can have multiple Students
👉 Each Student belongs to one College

---

## 📂 Project Structure

```
HibernateManyToOne/
│
├── src/main/java/
│   └── org/example/
│       ├── Main.java
│       └── entities/
│           ├── Student.java
│           └── StudentCollege.java
│
└── src/main/resources/
    └── hibernate.properties
```

---

## ⚙️ Technologies Used

* Java
* Hibernate ORM
* MySQL
* Maven (optional)

---

## 🔗 Mapping Explanation

### ➤ Many-to-One (Owning Side)

* Defined in `Student`
* Uses `@ManyToOne` and `@JoinColumn`

### ➤ One-to-Many (Inverse Side)

* Defined in `StudentCollege`
* Uses `@OneToMany(mappedBy = "studentCollege")`

---

## 🧱 Database Structure

### STUDENT Table

| STUDENT_ID | STUDENT_NAME | COLLEGE_FK |
| ---------- | ------------ | ---------- |
| 1          | Tom Cruise   | 1          |
| 2          | Will Smith   | 2          |
| 3          | Keanu Reeves | 1          |

### STUDENT_COLLEGE Table

| COLLEGE_ID | COLLEGE_NAME         |
| ---------- | -------------------- |
| 1          | Standford University |
| 2          | Princeton University |

---

## 📄 Entity Classes

### 🧑 Student.java

```java
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
}
```

---

### 🏫 StudentCollege.java

```java
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
}
```

---

## 🚀 Main Class

```java
package org.example;

import org.example.entities.Student;
import org.example.entities.StudentCollege;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.addAnnotatedClass(Student.class);
        config.addAnnotatedClass(StudentCollege.class);

        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            // Students
            Student tom = new Student();
            tom.setStudentName("Tom Cruise");

            Student will = new Student();
            will.setStudentName("Will Smith");

            Student keanu = new Student();
            keanu.setStudentName("Keanu Reeves");

            // Colleges
            Student standford = new StudentCollege();
            standford.setCollegeName("Standford University");

            Student princeton = new StudentCollege();
            princeton.setCollegeName("Princeton University");

            // Mapping
            tom.setStudentCollege(standford);
            will.setStudentCollege(princeton);
            keanu.setStudentCollege(standford);

            // Lists
            List<Student> standfordStudents = new ArrayList<>();
            standfordStudents.add(tom);
            standfordStudents.add(keanu);

            List<Student> princetonStudents = new ArrayList<>();
            princetonStudents.add(will);

            standford.setStudents(standfordStudents);
            princeton.setStudents(princetonStudents);

            // Save only colleges
            session.persist(standford);
            session.persist(princeton);

            session.getTransaction().commit();

        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
```

---

## ⚙️ Hibernate Configuration

### hibernate.properties

```properties
hibernate.connection.url=jdbc:mysql://localhost/test
hibernate.connection.driver_class=com.mysql.cj.jdbc.Driver
hibernate.dialect=org.hibernate.dialect.MySQLDialect
hibernate.connection.username=root
hibernate.connection.password=rootpassword

hibernate.show_sql=true
hibernate.format_sql=true
hibernate.hbm2ddl.auto=create
```

---

## 📦 Maven Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.4.4.Final</version>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>
</dependencies>
```

---

## ▶️ How to Run

1. Create MySQL database:

```sql
CREATE DATABASE test;
```

2. Update username/password in `hibernate.properties`

3. Run `Main.java`

---

## ✅ Output

* Tables will be created automatically
* Data inserted:

    * 3 Students
    * 2 Colleges
* Relationships maintained using foreign key

---

## 🔥 Key Points

* `Student` is **owning side**
* `StudentCollege` is **inverse side**
* `mappedBy` defines bidirectional relation
* `CascadeType.ALL` saves related entities automatically
* Only **colleges are persisted**, students auto-saved

---

## 📚 Conclusion

This project clearly demonstrates:

* Many-to-One mapping
* Bidirectional relationship
* Cascade operations
* Hibernate annotations usage

---

💡 Perfect for:

* Viva
* Practical exams
* Hibernate understanding
