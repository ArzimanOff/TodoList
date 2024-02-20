package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout notesListBox;
    private FloatingActionButton btnAddNote;
    private ArrayList<Note> notesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        Random random = new Random();
        for (int i = 0; i < 20; i++){
            Note note = new Note(i, "Note #" + i, random.nextInt(3));
            notesList.add(note);
        }
        showNotesList();

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NoteAddScreen.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        notesListBox = findViewById(R.id.notesListBox);
        btnAddNote = findViewById(R.id.btnAddNote);
    }

    private void showNotesList(){
        for (Note note : notesList ) {
            View noteView =  getLayoutInflater().inflate(
                    R.layout.note_item,
                    notesListBox,
                    false);
            TextView noteItem = noteView.findViewById(R.id.tvNoteItem);
            setNoteParams(noteItem, note); // устанавливаем нужные свойства
            notesListBox.addView(noteView); // добавляем уже настроенное view в список
        }
    }

    private int findColorOfNote(@NonNull Note note){
        int colorResId;
        switch (note.getPriority()) {
            case 0:
                colorResId = R.color.low_priority_active;
                break;
            case 1:
                colorResId = R.color.medium_priority_active;
                break;
            default:
                colorResId = R.color.high_priority_active;
                break;
        }
        int color = ContextCompat.getColor(this, colorResId);
        return color;
    }

    private void setNoteParams(TextView noteItem, Note note){
        noteItem.setText(note.getText()); // текст заметки
        noteItem.setBackgroundColor(findColorOfNote(note)); // цвет приоритета заметки
    }

}
