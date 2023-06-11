package com.example.deltaproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private ImageView startbutton;
    private MediaPlayer media3;
    private MediaPlayer media4;
    private TextView highscore,reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        startbutton=findViewById(R.id.pbutton);
        media4=MediaPlayer.create(this,R.raw.opening);
        media4.start();
        media4.setLooping(true);
        media3=MediaPlayer.create(this,R.raw.gameopen);
        reset=findViewById(R.id.reset);
        highscore=findViewById(R.id.highscore);
        SharedPreferences sharedPreferences = getSharedPreferences("highscorefile", MODE_PRIVATE);
        highscore.setText("Highscore:"+String.valueOf(sharedPreferences.getInt("highscorekey",0)));
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                media4.stop();
                media3.start();
                startActivity(intent);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showoff();
            }
        });
    }
    public void showoff(){
        final Dialog dialog=new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.rules);
        dialog.show();
    }

}