package com.example.todolist;

import java.util.ArrayList;
import java.util.Random;

public class Database {
    private ArrayList<Note> notesList = new ArrayList<>();

    private static Database instance = null;
    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    public Database(){
        Random random = new Random();
        for (int i = 0; i < 52; i++) {
            Note note = new Note(i, "Note #" + i, random.nextInt(3));
            notesList.add(note);
        }
    }
    public void add(Note note){
        notesList.add(note);
    }

    public void remove(int id){
        for (int i = 0; i < notesList.size(); i++){
            Note note = notesList.get(i);
            if (note.getId() == id){
                notesList.remove(note);
            }
        }
    }

    public ArrayList<Note> getNotesList(){
        return new ArrayList<>(notesList);
    }

}
