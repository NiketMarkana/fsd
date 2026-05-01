# Hibernate + JPA + MySQL Project (IntelliJ IDEA) – Complete Guide

---

## 📌 Project Objective

To create a simple **Hibernate (JPA) application** using:

* IntelliJ IDEA
* Maven
* MySQL Database

The project performs:
👉 Insert a `Student` record into database using Hibernate ORM.

---

## 🛠️ Technologies Used

* Java (JDK 17+)
* IntelliJ IDEA
* Maven
* Hibernate ORM (JPA)
* MySQL

---

## 🚀 Step 1: Create Project in IntelliJ

1. Open IntelliJ IDEA
2. Click **New Project**
3. Select **Maven**
4. Choose JDK
5. Set:

    * GroupId: `org.example`
    * ArtifactId: `HibernateDemo`
6. Click **Create**

---

## 📂 Step 2: Project Structure

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
            ├── META-INF
            │   └── persistence.xml
            └── hibernate.cfg.xml
```

---

## ⚙️ Step 3: Add Dependencies (`pom.xml`)

```xml
<dependencies>

    <!-- Hibernate -->
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>7.1.8.Final</version>
    </dependency>

    <!-- MySQL -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.7.0</version>
    </dependency>

</dependencies>
```

👉 After adding → Click **Load Maven Changes**

---

## 🧑‍💻 Step 4: Create Entity Class

### 📄 `Student.java`

```java
package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;

    public Student() {}

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
```

---

## ⚙️ Step 5: Create `persistence.xml`

📁 Path:
`src/main/resources/META-INF/persistence.xml`

```xml
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             version="3.1">

    <persistence-unit name="jpademo" transaction-type="RESOURCE_LOCAL">

        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

        <class>org.example.entities.Student</class>

        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/test"/>
            <property name="jakarta.persistence.jdbc.user" value="root"/>
            <property name="jakarta.persistence.jdbc.password" value="YOUR_PASSWORD"/>

            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
        </properties>

    </persistence-unit>

</persistence>
```

---

## ⚙️ Step 6: (Optional) `hibernate.cfg.xml`

```xml
<hibernate-configuration>
    <session-factory>

        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/test</property>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">YOUR_PASSWORD</property>

        <mapping class="org.example.entities.Student"/>

    </session-factory>
</hibernate-configuration>
```

---

## 💻 Step 7: Create Main Class

### 📄 `Main.java`

```java
package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.Student;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpademo");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student s = new Student("Niket", "niket@example.com");
        em.persist(s);

        em.getTransaction().commit();

        em.close();
        emf.close();

        System.out.println("Data inserted successfully!");
    }
}
```

---

## ❌ Step 8: First Run Error (IMPORTANT)

When running first time, error occurred:

```text
Unknown database 'test'
```

👉 This means database does not exist.

---

## ✅ Step 9: Fix Error

Open MySQL and run:

```sql
CREATE DATABASE test;
```

---

## ▶️ Step 10: Run Project Again

Run `Main.java`

---

## ✅ Final Output

```text
Hibernate: insert into students ...
Data inserted successfully!
```

---

## 🧾 Step 11: Verify Data in MySQL

```sql
USE test;
SELECT * FROM students;
```

Expected:

```text
id | name  | email
1  | Niket | niket@example.com
```

---

## ⚠️ Common Errors & Solutions

### 🔴 Error: Unknown database

✔ Fix:

```sql
CREATE DATABASE test;
```

---

### 🔴 Error: Connection Failed

✔ Check:

* MySQL running
* Username/password correct

---

### 🔴 Error: Driver issue

✔ Use:

```text
com.mysql.cj.jdbc.Driver
```

---

## 📌 Important Notes

* Use `persistence.xml` for JPA
* `hibernate.hbm2ddl.auto=update` auto creates table
* Remove old driver (`com.mysql.jdbc.Driver`)
* MySQL must be running

---

## 🎯 Conclusion

This project covers:

* Hibernate setup
* Maven configuration
* MySQL connection
* Entity mapping
* Data insertion
* Error debugging

---

## 👨‍💻 Author

**Niket Markana**

---
