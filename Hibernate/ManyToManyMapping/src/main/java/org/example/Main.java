// src/main/java/org/example/Main.java
package org.example;

import org.example.entities.Certification;
import org.example.entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.addAnnotatedClass(Student.class);
        config.addAnnotatedClass(Certification.class);

        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            Student tom = new Student();
            tom.setStudentName("Tom Cruise");

            Student will = new Student();
            will.setStudentName("Will Smith");

            Student keanu = new Student();
            keanu.setStudentName("Keanu Reeves");

            Certification awsCloud = new Certification();
            awsCloud.setCertificationName("AWS Certified Cloud Practitioner");

            Certification oracleJava = new Certification();
            oracleJava.setCertificationName("Oracle Certified Associate Java Programmer");

            Certification vmwareSpring = new Certification();
            vmwareSpring.setCertificationName("Spring Certified Professional");

            Set<Certification> tomCertificates = new HashSet<>();
            tomCertificates.add(oracleJava);
            tomCertificates.add(vmwareSpring);

            Set<Certification> willCertificates = new HashSet<>();
            willCertificates.add(awsCloud);

            Set<Certification> keanuCertificates = new HashSet<>();
            keanuCertificates.add(awsCloud);
            keanuCertificates.add(oracleJava);
            keanuCertificates.add(vmwareSpring);

            tom.setStudentCertificates(tomCertificates);
            will.setStudentCertificates(willCertificates);
            keanu.setStudentCertificates(keanuCertificates);

            session.persist(tom);
            session.persist(will);
            session.persist(keanu);

            session.getTransaction().commit();

            System.out.println("Many To Many Mapping Data Inserted Successfully");

        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}