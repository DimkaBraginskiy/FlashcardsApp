package org.example.flashcardsapp.controller;

import org.example.flashcardsapp.service.FileService;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class FlashcardsController {
    FileService fileService;

    public FlashcardsController(FileService fileService) {
        this.fileService = fileService;
    }

    public void startDialog(){
        boolean isValid = false;

        System.out.println(
                "Press one of the keys described below:\n" +
                        "1 -> Add a new word to dictionary;\n" +
                        "2 -> Display all words from dictionary;\n" +
                        "3 -> Take a test based on words from dictionary;");

        do{
            Scanner scanner = new Scanner(System.in);
            int val = -1;
            val = scanner.nextInt();
            switch (val) {
                case 1:
                    firstDialog();
                    isValid = true;
                    break;
                case 2:
                    secondDialog();
                    isValid = true;
                    break;
                case 3:
                    thirdDialog();
                    isValid = true;
                    break;
                default:
                    System.out.println("Wrong input.");
                    break;
            }
        } while (!isValid);

    }

    private void firstDialog(){

    }

    private void secondDialog(){

    }

    private void thirdDialog(){

    }
}
