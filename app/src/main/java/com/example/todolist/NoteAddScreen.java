package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class NoteAddScreen extends AppCompatActivity {

    Button btnCloseNoteAddingScreen;
    EditText etNoteTextInputBox;
    RadioGroup rgChoosePriority;
    Button btnSaveNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add_screen);
        initViews();

        btnCloseNoteAddingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setDefaultParamsToRadioGroup();
        rgChoosePriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // если кнопка RadioButton нажата:
                // делаем все остальные кнопки неактивными:
                doAllOtherRadioButtonsInactive(group, checkedId);
                // а нажатую делаем активной
                doRadioButtonActive(checkedId);
            }
        });
    }

    private void setDefaultParamsToRadioGroup(){
        doRadioButtonActive(R.id.rbLowPriority);
    }

    private void doRadioButtonActive(int checkedId){
        RadioButton checkedRadioButton = findViewById(checkedId);
        int bgActiveColorId;
        if (checkedId == R.id.rbLowPriority){
            bgActiveColorId = R.color.low_priority_active;
        } else if (checkedId == R.id.rbMediumPriority) {
            bgActiveColorId = R.color.medium_priority_active;
        } else {
            bgActiveColorId = R.color.high_priority_active;
        }

        // а для нажатой кнопки указываем настройки для активной кнопки:
        checkedRadioButton.setBackgroundResource(bgActiveColorId);
        checkedRadioButton.setTextColor(getResources().getColor(R.color.text_color));
        checkedRadioButton.setTypeface(null, Typeface.BOLD);
    }

    private void doRadioButtonInactive(RadioButton thisBtn, int thisBtnId){
        int bgInactiveColorId;
        if (thisBtnId == R.id.rbLowPriority){
            bgInactiveColorId = R.color.low_priority_inactive;
        } else if (thisBtnId == R.id.rbMediumPriority) {
            bgInactiveColorId = R.color.medium_priority_inactive;
        } else {
            bgInactiveColorId = R.color.high_priority_inactive;
        }
        thisBtn.setTextColor(getResources().getColor(R.color.inactive_rb_text_color));
        thisBtn.setTypeface(null, Typeface.NORMAL);
        thisBtn.setBackgroundResource(bgInactiveColorId);
    }

    private void doAllOtherRadioButtonsInactive(RadioGroup group, int checkedId){
        // проходимся по всем элементам внутри RadioGroup
        for (int i = 0; i < group.getChildCount(); i++){
            // если текущий элемент является кнопкой RadioButton
            if (group.getChildAt(i) instanceof RadioButton){
                // запоминаем текущую кнопку RadioButton
                RadioButton thisBtn = (RadioButton) group.getChildAt(i);
                // запоминаем её id
                int thisBtnId = thisBtn.getId();
                // если id текущей кнопки не равно id нажатой кнопки:
                if (thisBtnId != checkedId){
                    // делаем эту кнопку неактивной
                    doRadioButtonInactive(thisBtn, thisBtnId);
                }
            }
        }
    }

    private void initViews(){
        btnCloseNoteAddingScreen = findViewById(R.id.btnCloseNoteAddingScreen);
        etNoteTextInputBox = findViewById(R.id.etNoteTextInputBox);
        rgChoosePriority = findViewById(R.id.rgChoosePriority);
        btnSaveNote = findViewById(R.id.btnSaveNote);
    }
}