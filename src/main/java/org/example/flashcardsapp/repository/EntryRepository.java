package org.example.flashcardsapp.repository;

import org.example.flashcardsapp.entries.Entry;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class EntryRepository {
    List<Entry> entries = new ArrayList<Entry>();
    private final Random random;

    public EntryRepository(Random random, List<Entry> entries) {
        this.random = random;
        this.entries = entries;
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public List<Entry> getAllEntries() {
        return entries;
    }

    public Entry getRandomEntry(){
        return entries.get(random.nextInt(entries.size()));
    }
}
