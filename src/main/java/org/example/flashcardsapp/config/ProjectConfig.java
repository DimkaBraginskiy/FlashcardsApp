package org.example.flashcardsapp.config;

import org.example.flashcardsapp.entries.Entry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Random;
import java.util.Scanner;

@Configuration
@PropertySource("classpath:external.properties")
public class ProjectConfig {
    @Bean
    Scanner scanner() {
        return new Scanner(System.in);
    }
    @Bean
    Random random(){
        return new Random();
    }
}