package com.example.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Replaces web.xml — registers DispatcherServlet programmatically.
 */
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        // Create Spring Web Application Context
        AnnotationConfigWebApplicationContext context =
                new AnnotationConfigWebApplicationContext();

        // Register configuration class
        context.register(WebApplicationContextConfig.class);

        // Create DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        // Register DispatcherServlet
        ServletRegistration.Dynamic registration =
                servletContext.addServlet("dispatcher", dispatcherServlet);

        // Load on startup
        registration.setLoadOnStartup(1);

        // Map to root URL
        registration.addMapping("/");
    }
}