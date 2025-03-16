package org.example.flashcardsapp;

import org.example.flashcardsapp.controller.FlashcardsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FlashcardsApp {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);

		FlashcardsController flashcardsController = context.getBean(FlashcardsController.class);

		flashcardsController.start();
	}

}
