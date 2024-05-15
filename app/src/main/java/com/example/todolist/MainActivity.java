package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
        for (int i = 0; i < 52; i++) {
            Note note = new Note(i, "Note #" + i, random.nextInt(3));
            notesList.add(note);
        }
        showNotesList();
    }

    private void showNotesList() {
        for (Note note : notesList){
            View view = getLayoutInflater().inflate(
                    R.layout.note_item,
                    notesListBox,
                    false
            );
            TextView noteItemView = view.findViewById(R.id.tvNoteItem);
            noteItemView.setText(note.getText());

            int colorId;
            switch (note.getPriority()){
                case 0:
                    colorId = R.color.low_priority_active;
                    break;
                case 1:
                    colorId = R.color.medium_priority_active;
                    break;
                default:
                    colorId = R.color.high_priority_active;
            }

            int color = ContextCompat.getColor(this, colorId);
            noteItemView.setBackgroundColor(color);

            notesListBox.addView(view);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViews(){
        notesListBox = findViewById(R.id.notesListBox);
        btnAddNote = findViewById(R.id.btnAddNote);
    }

}
