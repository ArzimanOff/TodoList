package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

public class AddNoteActivity extends AppCompatActivity {

    private EditText NoteTextInputBox;
    private RadioGroup rgChoosePriority;
    private RadioButton rbLowPriority;
    private RadioButton rbMediumPriority;
    private RadioButton rbHighPriority;
    private MaterialButton btnSaveNote;
    private Button btnCloseNoteAddingScreen;
    private AddNoteViewModel addNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.getNoteAdded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean noteAdded) {
                // закрываем текущее активити
                // если процесс добавления заметки был выполнен
                // и noteAdded стало равны true
                if (noteAdded){
                    finish();
                }
            }
        });
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
            // если текст заметки пустой, выкидываем уведомление об этом и не создаем экземпляр Note
            ApplicationSignals.notifyEmptyNote(this);
            return;
        }
        int priority = getPriority();
        Note note = new Note(inputNoteText, priority);
        addNoteViewModel.saveNote(note);
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
        int lowPriorityTextColor = ContextCompat.getColor(this, R.color.low_priority_active);
        int mediumPriorityTextColor = ContextCompat.getColor(this, R.color.medium_priority_active);
        int highPrioriTytextColor = ContextCompat.getColor(this, R.color.high_priority_active);
        int inactive_rb_color = ContextCompat.getColor(this, R.color.inactive_rb_color);

        if (rbLowPriority.isChecked()){
            // активируем нужные стили для текущего радиобокса
            rbLowPriority.setBackgroundResource(R.drawable.low_priority_rb_bg);
            rbLowPriority.setTextColor(lowPriorityTextColor);

            // деактивируем нужные стили для остальных радиобоксов
            rbMediumPriority.setBackgroundResource(R.drawable.default_priority_rb_bg);
            rbMediumPriority.setTextColor(inactive_rb_color);

            rbHighPriority.setBackgroundResource(R.drawable.default_priority_rb_bg);
            rbHighPriority.setTextColor(inactive_rb_color);

        } else if (rbMediumPriority.isChecked()) {
            // активируем нужные стили для текущего радиобокса
            rbMediumPriority.setBackgroundResource(R.drawable.midium_priority_rb_bg);
            rbMediumPriority.setTextColor(mediumPriorityTextColor);

            // деактивируем нужные стили для остальных радиобоксов
            rbLowPriority.setBackgroundResource(R.drawable.default_priority_rb_bg);
            rbLowPriority.setTextColor(inactive_rb_color);

            rbHighPriority.setBackgroundResource(R.drawable.default_priority_rb_bg);
            rbHighPriority.setTextColor(inactive_rb_color);

        } else if (rbHighPriority.isChecked()){
            // активируем нужные стили для текущего радиобокса
            rbHighPriority.setBackgroundResource(R.drawable.high_priority_rb_bg);
            rbHighPriority.setTextColor(highPrioriTytextColor);

            // деактивируем нужные стили для остальных радиобоксов
            rbLowPriority.setBackgroundResource(R.drawable.default_priority_rb_bg);
            rbLowPriority.setTextColor(inactive_rb_color);

            rbMediumPriority.setBackgroundResource(R.drawable.default_priority_rb_bg);
            rbMediumPriority.setTextColor(inactive_rb_color);
        } else {
            throw new RuntimeException("It seems that none of the priorities have been chosen " +
                    "(Похоже ни один из приоритетов не выбран)");
        }
    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }

}