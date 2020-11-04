package com.example.assignment2;

import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
public class TimerActivity extends AppCompatActivity {
    private int seconds = 0;
    private int hours=0,minutes=0,secs=0;
    private boolean running = false;
    private EditText hours_edttxt, minutes_edtxt, seconds_edttxt;
    private TextView hours_txt, minutes_txt, seconds_txt;
    private Button start_btn;
    private Ringtone r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);
        hours_edttxt = findViewById(R.id.hours_edittxt);
        minutes_edtxt = findViewById(R.id.minutes_edittxt);
        seconds_edttxt = findViewById(R.id.seconds_edittxt);
        hours_txt = findViewById(R.id.hours_txtview);
        minutes_txt = findViewById(R.id.minutes_txtview);
        seconds_txt = findViewById(R.id.seconds_txtview);
        start_btn = findViewById(R.id.start_btn);
        checkSavedInstance(savedInstanceState);
    }

    private void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
         @Override
         public void run() {
             if (seconds<=0 && hours_txt.getVisibility()==View.VISIBLE){
                 running=false;
                 hours_edttxt.setVisibility(View.VISIBLE);
                 minutes_edtxt.setVisibility(View.VISIBLE);
                 seconds_edttxt.setVisibility(View.VISIBLE);
                 hours_txt.setVisibility(View.INVISIBLE);
                 minutes_txt.setVisibility(View.INVISIBLE);
                 seconds_txt.setVisibility(View.INVISIBLE);
                 try {
                     Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                     r = RingtoneManager.getRingtone(getApplicationContext(),notification );
                     r.play();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
             if (running) {
                 seconds--;
                 System.out.println("running   " + seconds);
                 hours = seconds / 3600;
                 minutes = (seconds % 3600) / 60;
                 secs = seconds % 60;
                 hours_txt.setText(String.valueOf(hours));
                 minutes_txt.setText(String.valueOf(minutes));
                 seconds_txt.setText(String.valueOf(secs));
                 handler.postDelayed(this, 1000);
             }
         }
     });
    }

    private void checkSavedInstance(Bundle savedInstanceState){
        if (savedInstanceState!= null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            System.out.println(savedInstanceState.getBoolean("running"));
            hours_edttxt.setVisibility(savedInstanceState.getInt("hEditText"));
            minutes_edtxt.setVisibility(savedInstanceState.getInt("hEditText"));
            seconds_edttxt.setVisibility(savedInstanceState.getInt("hEditText"));
            hours_txt.setVisibility(savedInstanceState.getInt("hText"));
            minutes_txt.setVisibility(savedInstanceState.getInt("mText"));
            seconds_txt.setVisibility(savedInstanceState.getInt("sText"));
            runTimer();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", true);
        outState.putInt("hEditText",hours_edttxt.getVisibility());
        outState.putInt("mEditText",minutes_edtxt.getVisibility());
        outState.putInt("sEditText",seconds_edttxt.getVisibility());
        outState.putInt("hText",hours_txt.getVisibility());
        outState.putInt("mText",minutes_txt.getVisibility());
        outState.putInt("sText",seconds_txt.getVisibility());
    }

    public void btnStartOnClick(View view){
        if (hours_edttxt.getVisibility()==View.VISIBLE)
            seconds = (Integer.parseInt(hours_edttxt.getText().toString())*3600)+(Integer.parseInt(minutes_edtxt.getText().toString())*60)+(Integer.parseInt(seconds_edttxt.getText().toString()));
        else
            seconds = (Integer.parseInt(hours_txt.getText().toString())*3600)+(Integer.parseInt(minutes_txt.getText().toString())*60)+(Integer.parseInt(seconds_txt.getText().toString()));
        hours_edttxt.setVisibility(View.INVISIBLE);
        minutes_edtxt.setVisibility(View.INVISIBLE);
        seconds_edttxt.setVisibility(View.INVISIBLE);
        hours_txt.setVisibility(View.VISIBLE);
        minutes_txt.setVisibility(View.VISIBLE);
        seconds_txt.setVisibility(View.VISIBLE);
        start_btn.setClickable(false);
        running = true;
        runTimer();
    }

    public void btnPauseClick(View view){
        running = false;
        start_btn.setClickable(true);
    }

    public void btnStopClick(View view){
        running = false;
        seconds = 0;
        hours_edttxt.setVisibility(View.VISIBLE);
        minutes_edtxt.setVisibility(View.VISIBLE);
        seconds_edttxt.setVisibility(View.VISIBLE);
        hours_txt.setVisibility(View.INVISIBLE);
        minutes_txt.setVisibility(View.INVISIBLE);
        seconds_txt.setVisibility(View.INVISIBLE);
        hours_edttxt.setText("00");
        minutes_edtxt.setText("00");
        seconds_edttxt.setText("00");
        start_btn.setClickable(true);
        if (r!=null)
            r.stop();
    }
}
