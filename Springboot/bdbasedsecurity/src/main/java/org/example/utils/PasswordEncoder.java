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