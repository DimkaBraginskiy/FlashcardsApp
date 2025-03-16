package org.example.flashcardsapp.service;

import org.example.flashcardsapp.entries.Entry;
import org.example.flashcardsapp.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class FileService {
    @Value("${pl.edu.pja.tpo02.filename}")
    private String fname;
    private final EntryRepository entryRepository;

    public FileService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public void readFromFile(){
        //convert txt to objects
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fname);

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");

                if(parts.length == 3){
                    String english = parts[0].trim();
                    String polish = parts[1].trim();
                    String german = parts[2].trim();
                    Entry entry = new Entry(english, polish, german);
                    entryRepository.addEntry(entry);
                }else{
                    System.out.println("! Can not resolve line format: " + line);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addToFile(Entry entry){
        try {
            FileWriter fileWriter = new FileWriter("src\\main\\resources\\flashcards.txt", true);
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            PrintWriter writer = new PrintWriter(buffWriter);
            String english = entry.getEnglish();
            String polish = entry.getPolish();
            String german = entry.getGerman();

            writer.print("\n"+english+","+polish+","+german);

            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
