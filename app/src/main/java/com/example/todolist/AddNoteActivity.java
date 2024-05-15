package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class AddNoteActivity extends AppCompatActivity {

    EditText NoteTextInputBox;
    RadioButton rbLowPriority;
    RadioButton rbMediumPriority;
    RadioButton rbHighPriority;
    MaterialButton btnSaveNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void initViews() {
        NoteTextInputBox = findViewById(R.id.etNoteTextInputBox);
        rbLowPriority = findViewById(R.id.rbLowPriority);
        rbMediumPriority = findViewById(R.id.rbMediumPriority);
        rbHighPriority = findViewById(R.id.rbHighPriority);
        btnSaveNote = findViewById(R.id.btnSaveNote);
    }

    private void saveNote() {
        String inputNoteText = NoteTextInputBox.getText().toString().trim();
        if (inputNoteText.isEmpty()){
            Toast.makeText(this,
                    "Заметка не может быть пустой!",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        int priority = getPriority();
        if (priority == -1){
            Toast.makeText(this,
                    "Выберите уровень приоритета!",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
    }

    private int getPriority(){
        int priority;
        if (rbLowPriority.isChecked()){
            priority = 0;
        } else if (rbMediumPriority.isChecked()) {
            priority = 1;
        } else if (rbHighPriority.isChecked()){
            priority = 2;
        } else {
            priority = -1;
        }
        return priority;
    }


    public static Intent newIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }

}