package com.example.studentunion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        Timer timer = new Timer();
        TimerTask task;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(index.this, Log_01_Log.class));
            }
        }, 4000);
    }
    
    public void task(){
        
    }
}