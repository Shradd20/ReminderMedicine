package com.example.medicine_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText descriptionEditText;
    private TimePicker timePicker;
    private Button setAlarmButton;
    private TextView alarmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        timePicker = findViewById(R.id.timePicker);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        alarmTextView = findViewById(R.id.descriptionTextView);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionEditText.getText().toString().trim();
                if (description.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a description for the alarm", Toast.LENGTH_SHORT).show();
                    return;
                }
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                long alarmTimeInMillis = calendar.getTimeInMillis();
                setAlarm(alarmTimeInMillis, description);
                String alarmTimeString = "Alarm set for " + hour + ":" + minute;
                alarmTextView.setText(alarmTimeString);
                Toast.makeText(MainActivity.this, "Reminder has been set", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAlarm(long alarmTimeInMillis, String description) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("description", description);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);
    }
}