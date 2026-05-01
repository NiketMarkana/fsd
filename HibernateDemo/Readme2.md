# Hibernate (SessionFactory) + MySQL Project

---

## 📌 Project Objective

To implement a basic **Hibernate ORM application using SessionFactory** with:

* XML configuration (`hibernate.cfg.xml`)
* MySQL database
* Simple insert operation using `Session`

---

## 🛠️ Technologies Used

* Java (JDK 17+)
* IntelliJ IDEA
* Maven
* Hibernate ORM (Core API)
* MySQL

---

## 📂 Project Structure

```text
HibernateDemo
│
├── pom.xml
│
└── src
    └── main
        ├── java
        │   └── org.example
        │       ├── Main.java
        │       └── entities
        │           └── Student.java
        │
        └── resources
            └── hibernate.cfg.xml
```

---

## ⚙️ Step 1: Create Database

Open MySQL and run:

```sql
CREATE DATABASE test;
```

---

## ⚙️ Step 2: Add Dependencies (`pom.xml`)

```xml
<dependencies>

    <!-- Hibernate Core -->
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>7.1.8.Final</version>
    </dependency>

    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.7.0</version>
    </dependency>

</dependencies>
```

---

## ⚙️ Step 3: Hibernate Configuration (`hibernate.cfg.xml`)

📁 Path: `src/main/resources/hibernate.cfg.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>

<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "https://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>

    <session-factory>

        <!-- Database connection -->
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/test</property>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">rootpassword</property>

        <!-- Hibernate properties -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.format_sql">true</property>
        <property name="hibernate.hbm2ddl.auto">update</property>

        <!-- Mapping class -->
        <mapping class="org.example.entities.Student"/>

    </session-factory>

</hibernate-configuration>
```
hibernate.properties
```xml
hibernate.connection.url=jdbc:mysql: //Localhost/test
hibernate.connection.driver_class=com.mysql.jdbc.Driver
hibernate.dialect=org.hibernate.dialect.MySQLDialect
        
hibernate.connection.username=root
hibernate.connection. password=rootpassword
hibernate.show_sql=true
hibernate. format_sql=true
hibernate.hbm2ddL.auto=create
```
---

## ⚙️ Step 4: Entity Class (`Student.java`)

```java
package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private int id;

    private String studentName;

    public Student() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
```

---

## ⚙️ Step 5: Main Class (`Main.java`)

```java
package org.example;

import org.example.entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {

        // Load configuration
        Configuration config = new Configuration();
        config.configure();
        //config.addAnnotatedClass(org.example.entities.Student.class); 

        // Create SessionFactory
        SessionFactory sessionFactory = config.buildSessionFactory();

        // Open session
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            // Create object
            Student student = new Student();
            student.setId(4);
            student.setStudentName("Anna Smith");

            // Save data
            session.persist(student);

            session.getTransaction().commit();

            System.out.println("Data inserted successfully!");

        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
```

---

## ▶️ How to Run

1. Open project in IntelliJ
2. Load Maven dependencies
3. Ensure MySQL is running
4. Run `Main.java`

---

## ✅ Expected Output

```text
Hibernate: insert into students ...
Data inserted successfully!
```

---

## 🧾 Verify in MySQL

```sql
USE test;
SELECT * FROM students;
```

---

## ❗ Common Errors & Fix

### 🔴 Error: Unknown database 'test'

✔ Fix:

```sql
CREATE DATABASE test;
```

---

### 🔴 Error: Connection failed

✔ Check:

* MySQL is running
* Username/password correct

---

### 🔴 Error: Driver issue

✔ Use:

```text
com.mysql.cj.jdbc.Driver
```

---

## 📌 Important Notes

* Uses **Hibernate Core (SessionFactory)** approach
* XML configuration instead of `persistence.xml`
* `hibernate.hbm2ddl.auto=update` creates table automatically
* Must match database name in URL

---

## 🎯 Conclusion

This project demonstrates:

* Hibernate configuration using XML
* SessionFactory and Session usage
* Entity mapping
* Insert operation
* MySQL connectivity

---

## 👨‍💻 Author

**Niket Markana**

---
