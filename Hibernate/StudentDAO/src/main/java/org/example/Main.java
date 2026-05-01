package org.example;

import org.example.dao.StudentDAO;
import org.example.dao.StudentDAOImpl;
import org.example.entities.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Configuration config = new Configuration();

        config.addAnnotatedClass(Student.class);

        SessionFactory sessionFactory = config.buildSessionFactory();

        StudentDAO studentDAO = new StudentDAOImpl(sessionFactory);

        Student student1 = new Student();
        student1.setStudentName("Tom Cruise");

        Student student2 = new Student();
        student2.setStudentName("Will Smith");

        System.out.println("Adding student " + student1);
        System.out.println("Adding student " + student2);

        studentDAO.saveStudent(student1);
        studentDAO.saveStudent(student2);

        List<Student> students = studentDAO.getAllStudents();
        System.out.println("Students: " + students);

        Student studentToUpdate = studentDAO.getStudentById(1);

        if (studentToUpdate != null) {
            System.out.println("Updating student " + studentToUpdate);

            studentToUpdate.setStudentName("Tomkumar Cruise");
            studentDAO.updateStudent(studentToUpdate);
        }

        Student updatedStudent = studentDAO.getStudentById(1);
        System.out.println("Updated student = " + updatedStudent);

        studentDAO.deleteStudent(2);
        System.out.println("Deleting student with id = 2");

        List<Student> studentsAfterDelete = studentDAO.getAllStudents();
        System.out.println("Students after delete: " + studentsAfterDelete);

        sessionFactory.close();
    }
}