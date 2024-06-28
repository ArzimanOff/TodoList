package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class AddNoteActivity extends AppCompatActivity {

    private EditText NoteTextInputBox;
    private RadioGroup rgChoosePriority;
    private RadioButton rbLowPriority;
    private RadioButton rbMediumPriority;
    private RadioButton rbHighPriority;
    private MaterialButton btnSaveNote;
    private Button btnCloseNoteAddingScreen;

    private Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();
        updateStyleChoosePriorityRadioGroup();
        // слушатель на кнопку закрытия экрана
        btnCloseNoteAddingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // слушатель на кнопку сохранения заметки
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        // слушатель на группу радиокнопок для изменения приоритета
        rgChoosePriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateStyleChoosePriorityRadioGroup();
            }
        });
    }

    private void initViews() {
        NoteTextInputBox = findViewById(R.id.etNoteTextInputBox);
        rgChoosePriority = findViewById(R.id.rgChoosePriority);
        rbLowPriority = findViewById(R.id.rbLowPriority);
        rbMediumPriority = findViewById(R.id.rbMediumPriority);
        rbHighPriority = findViewById(R.id.rbHighPriority);
        btnSaveNote = findViewById(R.id.btnSaveNote);
        btnCloseNoteAddingScreen = findViewById(R.id.btnCloseNoteAddingScreen);
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
        int id = database.getNotesList().size();

        Note note = new Note(id, inputNoteText, priority);
        database.add(note);
        finish();
    }

    private int getPriority(){
        int priority;

        if (rbLowPriority.isChecked()){
            priority = 0;
        } else if (rbMediumPriority.isChecked()) {
            priority = 1;
        } else{
            priority = 2;
        }
        return priority;
    }

    public void updateStyleChoosePriorityRadioGroup(){
        int textColor = ContextCompat.getColor(this, R.color.text_color);
        int inactive_rb_text_color = ContextCompat.getColor(this, R.color.inactive_rb_text_color);
        if (rbLowPriority.isChecked()){
            // активируем нужные стили для текущего радиобокса
            rbLowPriority.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.low_priority_active)
            );
            rbLowPriority.setTextColor(textColor);

            // деактивируем нужные стили для остальных радиобоксов
            rbMediumPriority.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.medium_priority_inactive)
            );
            rbMediumPriority.setTextColor(inactive_rb_text_color);

            rbHighPriority.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.high_priority_inactive)
            );
            rbHighPriority.setTextColor(inactive_rb_text_color);

        } else if (rbMediumPriority.isChecked()) {
            // активируем нужные стили для текущего радиобокса
            rbMediumPriority.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.medium_priority_active)
            );
            rbMediumPriority.setTextColor(textColor);

            // деактивируем нужные стили для остальных радиобоксов
            rbLowPriority.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.low_priority_inactive)
            );
            rbLowPriority.setTextColor(inactive_rb_text_color);

            rbHighPriority.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.high_priority_inactive)
            );
            rbHighPriority.setTextColor(inactive_rb_text_color);

        } else if (rbHighPriority.isChecked()){
            // активируем нужные стили для текущего радиобокса
            rbHighPriority.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.high_priority_active)
            );
            rbHighPriority.setTextColor(textColor);

            // деактивируем нужные стили для остальных радиобоксов
            rbLowPriority.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.low_priority_inactive)
            );
            rbLowPriority.setTextColor(inactive_rb_text_color);

            rbMediumPriority.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.medium_priority_inactive)
            );
            rbMediumPriority.setTextColor(inactive_rb_text_color);

        } else {
            throw new RuntimeException("It seems that none of the priorities have been chosen " +
                    "(Похоже ни один из приоритетов не выбран)");
        }
    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }

}