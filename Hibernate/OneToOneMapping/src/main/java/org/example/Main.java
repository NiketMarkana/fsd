package org.example;

import org.example.entities.Student;
import org.example.entities.StudentDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.addAnnotatedClass(Student.class);
        config.addAnnotatedClass(StudentDetail.class);

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            Student student = new Student();
            student.setStudentName("Tom Cruise");

            StudentDetail detail = new StudentDetail();
            detail.setZipCode(387001);

            // 🔥 IMPORTANT (set both sides)
            student.setStudentDetail(detail);
            detail.setStudent(student);

            session.persist(student);

            session.getTransaction().commit();
            System.out.println("Bidirectional Data Inserted");

        } finally {
            session.close();
            factory.close();
        }
    }
}