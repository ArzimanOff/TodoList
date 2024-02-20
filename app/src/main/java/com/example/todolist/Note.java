package com.example.todolist;

public class Note {
    private int id;
    private String text;
    private int priority;

    public Note(int id, String text, int priority) {
        this.id = id;
        this.text = text;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
