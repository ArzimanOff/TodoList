package com.example.todolist;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class ApplicationSignals {
    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            // Проверяем, поддерживает ли устройство вибрацию
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(
                        VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
                );
            }
        }
    }

    public static void notifyEmptyNote(Context context){
        Toast.makeText(context,
                "Заметка не может быть пустой!",
                Toast.LENGTH_SHORT
        ).show();
    }

}
