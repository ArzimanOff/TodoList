package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout notesListBox;
    private FloatingActionButton btnNewNote;
    private Database database = Database.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        btnNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // вызываем функцию отрисовки заметок каждый раз когда активити получает фокус
        showNotesList();
    }

    private void showNotesList() {
        // очищаем от старых заметок чтобы  добавить новые
        notesListBox.removeAllViews();
        for (Note note : database.getNotesList()){
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

    private void initViews(){
        notesListBox = findViewById(R.id.notesListBox);
        btnNewNote = findViewById(R.id.btnNewNote);
    }

}
