package com.example.alarmportion

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var time: Long = 0;
        val chooseTime: EditText = findViewById(R.id.editTextTime);
        val alarmButton: Button = findViewById(R.id.alarmButton);
        val adjustedAlarmTime: EditText = findViewById(R.id.adjustedalarmtime)
        chooseTime.setOnClickListener(View.OnClickListener() {

             val cal = Calendar.getInstance()
             val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                 cal.set(Calendar.HOUR_OF_DAY,hour)
                 cal.set(Calendar.MINUTE, minute)
                 if (System.currentTimeMillis() < cal.timeInMillis) {
                     var one = cal.timeInMillis;
                     time = cal.timeInMillis
                     var two = System.currentTimeMillis();
                     adjustedAlarmTime.setText("Today");
                     chooseTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
                 }
                 else {
                     time = cal.timeInMillis + 86400000
                     adjustedAlarmTime.setText("Tommorow");
                     chooseTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
                 }

             }
            TimePickerDialog(this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        });

        alarmButton.setOnClickListener(View.OnClickListener {
            Log.d("Set Alarm", time.toString())
            setAlarm(time);
        })




    }
    
    private fun setAlarm(timeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager;

        val intent = Intent(this,MyAlarm::class.java);
        val pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setExact(
            AlarmManager.RTC,
            timeInMillis,
            pendingIntent
        );
        Toast.makeText(this,"Alarm has been Set.",Toast.LENGTH_SHORT).show();

    }





}
public class MyAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Alarm Bell", "Alarm Just Fired");
        var mediaPlayer = MediaPlayer.create(context, R.raw.alarmsound);
        mediaPlayer.start()

    }


}