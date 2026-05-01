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

        Student student = new Student("Niket", "niket@example.com");

        em.persist(student);

        em.getTransaction().commit();

        em.close();
        emf.close();

        System.out.println("Student inserted successfully!");
    }
}