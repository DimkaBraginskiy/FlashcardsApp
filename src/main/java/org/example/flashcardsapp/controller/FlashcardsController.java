package org.example.flashcardsapp.controller;

import org.example.flashcardsapp.entries.Entry;
import org.example.flashcardsapp.repository.EntryRepository;
import org.example.flashcardsapp.service.DisplayService;
import org.example.flashcardsapp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.w3c.dom.ls.LSOutput;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Controller
public class FlashcardsController {
    private final FileService fileService;
    private final EntryRepository entryRepository;
    private final DisplayService displayService;
    private final Scanner scanner;

    public FlashcardsController(FileService fileService, EntryRepository entryRepository, DisplayService displayService, Scanner scanner) {
        this.fileService = fileService;
        this.entryRepository = entryRepository;
        this.displayService = displayService;
        this.scanner = scanner;
    }

    public void start(){
        fileService.readFromFile();

        while(true){
            printInfo();
            if(scanner.hasNextInt()){
                int val = scanner.nextInt();
                scanner.nextLine();
                switch (val) {
                    case 1:
                        firstDialog(scanner);
                        break;
                    case 2:
                        secondDialog();
                        break;
                    case 3:
                        thirdDialog(scanner);
                        break;
                    case 4:
                        System.out.println("Exiting program...");
                        try {
                            scanner.close();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        return;
                    default:
                        System.out.println("Wrong input.");
                        break;
                }
            }
        }
    }

    private void printInfo(){
        System.out.println(
                "Press one of the keys described below:\n" +
                        "1 -> Add a new word to dictionary;\n" +
                        "2 -> Display all words from dictionary;\n" +
                        "3 -> Take a test based on words from dictionary;\n" +
                        "4 -> Exit the program");
    }

    private void firstDialog(Scanner scanner){
        System.out.println("Enter word in English:");
        String english = scanner.nextLine().trim();
        System.out.println("Enter a translation in Polish:");
        String polish = scanner.nextLine().trim();
        System.out.println("Enter a translation in German:");
        String german = scanner.nextLine().trim();
        Entry entry = new Entry(english,polish,german);

        if(!entryRepository.getAllEntries().contains(entry)){
            fileService.addToFile(entry);
            entryRepository.addEntry(entry);
        }else{
            System.out.println("Entry already exists");
        }
        System.out.println("The word was added to the dictionary.");
    }

    private void secondDialog(){
        List<Entry> entries = entryRepository.getAllEntries();
        for(Entry entry : entries){
            String english = displayService.format(entry.getEnglish());
            String polish = displayService.format(entry.getPolish());
            String german = displayService.format(entry.getGerman());
            System.out.println("English: " + english + ", Polish: " + polish + ", German: " + german);
        }
    }

    private void thirdDialog(Scanner scanner){
        List<Entry> entries = entryRepository.getAllEntries();
        int amount = 0;
        int score = 0;
        boolean valid = false;

        Collections.shuffle(entries);//Shuffling so Entries order will not repeat.


        do{
            System.out.println("Total number of words: " + entries.size()+
                    "\nEnter amount of words you want to take a test from 0 to " + entries.size()+": ");
            if(scanner.hasNextInt()){
                amount = scanner.nextInt();
                scanner.nextLine();

                if(amount > 0 && amount <= entries.size()){
                    valid = true;
                }else{
                    System.out.println("Wrong input: Enter number in range from 0 to " + (entries.size()));
                }
            }else{
                System.out.println("Wrong input: Not a number");
            }
        }while(!valid);


        for(int i = 0; i < amount; i++){
            System.out.println("\nQuestion " + (i+1));
            System.out.println("Write Polish translation for: " + entries.get(i).getEnglish());
            String polish = scanner.nextLine().trim();

            System.out.println("Write German translation for: " + entries.get(i).getEnglish());
            String german = scanner.nextLine().trim();

            if(entries.get(i).getPolish().equalsIgnoreCase(polish) && entries.get(i).getGerman().equalsIgnoreCase(german)){
                System.out.println("Correct!");
                score++;
            }else{
                System.out.println("Incorrect!");
                System.out.println("Your Polish answer: " + polish + "; Correct answer: " + entries.get(i).getPolish());
                System.out.println("Your German answer: " + german + "; Correct answer: " + entries.get(i).getGerman());
            }
        }
        System.out.println("You scored: " + score+"/"+amount+"; Accuracy: " + (score*100)/amount+"%.");
    }
}
