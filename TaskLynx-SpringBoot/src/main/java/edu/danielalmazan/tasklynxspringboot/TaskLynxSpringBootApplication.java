package edu.danielalmazan.tasklynxspringboot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskLynxSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskLynxSpringBootApplication.class, args);
    }
}
