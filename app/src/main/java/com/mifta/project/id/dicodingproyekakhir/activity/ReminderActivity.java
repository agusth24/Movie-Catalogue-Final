package com.mifta.project.id.dicodingproyekakhir.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.notification.DailyReminderReceiver;
import com.mifta.project.id.dicodingproyekakhir.notification.ReleaseReminderReceiver;

public class ReminderActivity extends AppCompatActivity {
    public static String DAILY;
    public static String RELEASE;
    private static String SETTING_PREFS = "";
    private Switch dailySwitch;
    private Switch releaseSwitch;
    private boolean dailyCheck = false;
    private boolean releaseCheck = false;
    private ReleaseReminderReceiver releaseReminderReceiver;
    private DailyReminderReceiver dailyReminderReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        releaseReminderReceiver = new ReleaseReminderReceiver();
        dailyReminderReceiver = new DailyReminderReceiver();

        dailySwitch = findViewById(R.id.sw_daily);
        releaseSwitch = findViewById(R.id.sw_release);

        loadPref();
        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean dailyIsChecked) {
                dailyCheck = dailyIsChecked;
                setPref();
                if (dailyIsChecked) {
                    dailyReminderReceiver.dailyReminderOn(ReminderActivity.this);
                } else {
                    dailyReminderReceiver.dailyReminderOff(ReminderActivity.this);
                }
            }
        });
        releaseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean releaseIsChecked) {
                releaseCheck = releaseIsChecked;
                setPref();
                if (releaseIsChecked) {
                    releaseReminderReceiver.releaseReminderOn(ReminderActivity.this);
                } else {
                    releaseReminderReceiver.releaseReminderOff(ReminderActivity.this);
                }
            }
        });

    }

    private void setPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTING_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DAILY, dailyCheck);
        editor.putBoolean(RELEASE, releaseCheck);
        editor.apply();
    }

    private void loadPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTING_PREFS, Context.MODE_PRIVATE);
        dailySwitch.setChecked(sharedPreferences.getBoolean(DAILY, false));
        releaseSwitch.setChecked(sharedPreferences.getBoolean(RELEASE, false));
    }
}
