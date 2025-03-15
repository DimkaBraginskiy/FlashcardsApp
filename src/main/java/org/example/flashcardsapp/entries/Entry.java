package org.example.flashcardsapp.entries;


public class Entry {
    private String english;
    private String polish;
    private String german;

    public Entry(String english, String polish, String german) {
        this.english = english;
        this.polish = polish;
        this.german = german;
    }

    public String getEnglish() {
        return english;
    }

    public String getPolish() {
        return polish;
    }

    public String getGerman() {
        return german;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public void setGerman(String german) {
        this.german = german;
    }

    @Override
    public String toString() {
        return "English: " + english + ", Polish: " + polish + ", German: " + german;
    }
}
