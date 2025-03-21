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
                        thirdDialog(scanner, randomDecision());
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

    private boolean randomDecision(){
        boolean isValid = false;
        do{
            System.out.println("Would you like to get duplicate random flashcards ot not?\n" +
                    "1 -> Yes\n" +
                    "2 -> No\n");
            int input = scanner.nextInt();
            if(input == 1){
                return true;
            }else if(input == 2){
                return false;
            }else{
                System.out.println("Wrong input.");
                isValid = false;
            }
        }while(!isValid);
        return false;
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

        boolean isDuplicate = false;
        for(Entry OrigEntry : entryRepository.getAllEntries()){
            if(OrigEntry.getEnglish().equalsIgnoreCase(english)
                    && OrigEntry.getPolish().equalsIgnoreCase(polish)
                    && OrigEntry.getGerman().equalsIgnoreCase(german)){
                isDuplicate = true;
                break;
            }
        }

        if(isDuplicate){
            System.out.println("Can not add a word as it already exists.");
        }else{
            fileService.addWord(entry);
            System.out.println("The word was added to the dictionary.");
        }
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

    private void thirdDialog(Scanner scanner, boolean randomPopup){
        String polish;
        String german;

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
            if(randomPopup){
                score += askQuestion(entryRepository.getRandomEntry());
            }else{
                score += askQuestion(entries.get(i));
            }
        }
        System.out.println("You scored: " + score+"/"+amount+"; Accuracy: " + (score*100)/amount+"%.");
    }

    private int askQuestion(Entry entry){
        String polish;
        String german;

        System.out.println("Write Polish translation for: " + entry.getEnglish());
        polish = scanner.nextLine().trim();

        System.out.println("Write German translation for: " + entry.getEnglish());
        german = scanner.nextLine().trim();

        if(entry.getPolish().equalsIgnoreCase(polish) && entry.getGerman().equalsIgnoreCase(german)){
            System.out.println("Correct!");
            return 1;
        }else{
            System.out.println("Incorrect!");
            System.out.println("Your Polish answer: " + polish + "; Correct answer: " + entry.getPolish());
            System.out.println("Your German answer: " + german + "; Correct answer: " + entry.getGerman());
            return 0;
        }
    }
}
