package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class MainActivity extends AppCompatActivity {

    private LinearLayout notesListBox;
    private FloatingActionButton btnAddNote;
    private Database database = Database.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NoteAddScreen.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotesList();
    }

    private void initViews(){
        notesListBox = findViewById(R.id.notesListBox);
        btnAddNote = findViewById(R.id.btnAddNote);
    }

    private void showNotesList(){
        notesListBox.removeAllViews();
        for (Note note : database.getNotesList() ) {
            View noteView =  getLayoutInflater().inflate(
                    R.layout.note_item,
                    notesListBox,
                    false);

            noteView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    database.removeNote(note.getId());
                    showNotesList();

                    // Вызов вибрации
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibrator != null && vibrator.hasVibrator()) {
                        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                    }

                    return true;
                }
            });

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
