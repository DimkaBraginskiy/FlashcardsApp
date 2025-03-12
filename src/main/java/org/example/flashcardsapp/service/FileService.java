package org.example.flashcardsapp.service;

import org.example.flashcardsapp.repository.EntryRepository;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    EntryRepository entryRepository;

    public FileService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    private void readFromFile(){
        //convert txt to objects
        //add to list
    }
}
