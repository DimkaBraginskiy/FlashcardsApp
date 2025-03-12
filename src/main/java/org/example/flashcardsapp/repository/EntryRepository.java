package org.example.flashcardsapp.repository;

import org.example.flashcardsapp.entries.Entry;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EntryRepository {
    List<Entry> entries = new ArrayList<Entry>();

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public List<Entry> getAllEntries() {
        return entries;
    }

    public void getRandomEntry(){

    }
}
