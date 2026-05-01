// src/main/java/org/example/Main.java

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

            // Create three students
            Student tom = new Student();
            tom.setStudentName("Tom Cruise");

            Student will = new Student();
            will.setStudentName("Will Smith");

            Student keanu = new Student();
            keanu.setStudentName("Keanu Reeves");

            // Create two colleges
            StudentCollege standford = new StudentCollege();
            standford.setCollegeName("Standford University");

            StudentCollege princeton = new StudentCollege();
            princeton.setCollegeName("Princeton University");

            // Set college for every student
            tom.setStudentCollege(standford);
            will.setStudentCollege(princeton);
            keanu.setStudentCollege(standford);

            // Create list of students for Standford college
            List<Student> standfordStudents = new ArrayList<>();
            standfordStudents.add(tom);
            standfordStudents.add(keanu);

            // Create list of students for Princeton college
            List<Student> princetonStudents = new ArrayList<>();
            princetonStudents.add(will);

            // Set students list in colleges
            standford.setStudents(standfordStudents);
            princeton.setStudents(princetonStudents);

            // Store only colleges
            // Students will be stored automatically because of CascadeType.ALL
            session.persist(standford);
            session.persist(princeton);

            session.getTransaction().commit();

            System.out.println("Data inserted successfully!");

        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}