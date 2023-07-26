package com.example.deltaproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.color.utilities.Score;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {
    private ImageView startbutton;
    private MediaPlayer media3;
    private MediaPlayer media4;
    private Button chars,chasers;
    private TextView highscore,reset;
    private SharedPreferences sharedPreferences;
    private static final String url="https://api-obstacle-dodge.vercel.app";
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    TextView txt5;
    TextView txt6;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    public static String str,str2;
    private EditText edtt;
    private int score;
    private Button scorebutton;
    Data date;
    Data.Scores ne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        startbutton=findViewById(R.id.pbutton);
        chars=findViewById(R.id.characters);
        edtt=findViewById(R.id.edt);
        score=getIntent().getIntExtra("score",0);
        date=new Data();
        ne=date.new Scores();
        ne.setname(edtt.getText().toString());
        ne.setScore(score);
        scorebutton=findViewById(R.id.scorebutton);
        media4=MediaPlayer.create(this,R.raw.opening);
        media4.start();
        media4.setLooping(true);
        media3=MediaPlayer.create(this,R.raw.gameopen);
        reset=findViewById(R.id.reset);
        highscore=findViewById(R.id.highscore);
        chasers=findViewById(R.id.chasers);
        sharedPreferences = getSharedPreferences("highscorefile", MODE_PRIVATE);
        highscore.setText("Highscore:"+String.valueOf(sharedPreferences.getInt("highscorekey",0)));
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showoff2();
            }
        });
        chars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showoff2();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showoff();
            }
        });
        scorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showoff3();
            }
        });
        chasers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showoff4();
            }
        });
    }

    private void showoff4() {
        final Dialog dialog=new Dialog(MainActivity2.this,R.style.dialogT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.charac);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        txt1=dialog.findViewById(R.id.pa1);
        txt2=dialog.findViewById(R.id.pa2);
        txt3=dialog.findViewById(R.id.pa3);
        txt4=dialog.findViewById(R.id.pa4);
        txt5=dialog.findViewById(R.id.pa5);
        txt6=dialog.findViewById(R.id.pa6);
        img1=dialog.findViewById(R.id.img);
        img2=dialog.findViewById(R.id.img2);
        img3=dialog.findViewById(R.id.img3);
        Service service = retrofit.create(Service.class);
        CharReq request1 = new CharReq("chaser");
        Call<Data> call=service.getallcharacters(request1);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()) {
                    ArrayList<Data.Characters> details = response.body().getcharacters();
                    if (details != null && !details.isEmpty()) {
                        txt1.setText(details.get(0).getName());
                        txt3.setText(details.get(1).getName());
                        txt5.setText(details.get(2).getName());
                        txt2.setText(details.get(0).getDesc());
                        txt4.setText(details.get(1).getDesc());
                        txt6.setText(details.get(2).getDesc());
                        String imageUrl1 = details.get(0).getimageUrl();
                        String imageUrl2 = details.get(1).getimageUrl();
                        String imageUrl3 = details.get(2).getimageUrl();
                        img1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                str2=imageUrl1;
                                Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                                media4.setLooping(false);
                                media4.stop();
                                media3.start();
                                startActivity(intent);
                            }
                        });
                        img2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                str2=imageUrl2;
                                Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                                media4.setLooping(false);
                                media4.stop();
                                media3.start();
                                startActivity(intent);
                            }
                        });
                        img3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                str2=imageUrl3;
                                Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                                media4.setLooping(false);
                                media4.stop();
                                media3.start();
                                startActivity(intent);
                            }
                        });
                        Picasso.get().load(imageUrl1).into(img1);
                        Picasso.get().load(imageUrl2).into(img2);
                        Picasso.get().load(imageUrl3).into(img3);

                        Log.e("api", "name:" + details.get(0).getName());
                        Log.e("api", "name:" + details.get(1).getName());
                        Log.e("api", "name:" + details.get(2).getName());


                    }
                } else {
                    Log.e("API", "Error: " + response.code() + " - " + response.message());
                    //tip.setText("Error: " + response.code());
                    Toast.makeText(MainActivity2.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                //tip.setText("itsfailure");
                Log.d("API","Error");
                Toast.makeText(MainActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public void showoff(){
        final Dialog dialog=new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.rules);
        dialog.show();
    }
    public void showoff2(){
        final Dialog dialog=new Dialog(MainActivity2.this,R.style.dialogT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.charac);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         txt1=dialog.findViewById(R.id.pa1);
         txt2=dialog.findViewById(R.id.pa2);
         txt3=dialog.findViewById(R.id.pa3);
         txt4=dialog.findViewById(R.id.pa4);
         txt5=dialog.findViewById(R.id.pa5);
         txt6=dialog.findViewById(R.id.pa6);
         img1=dialog.findViewById(R.id.img);
         img2=dialog.findViewById(R.id.img2);
         img3=dialog.findViewById(R.id.img3);
        Service service = retrofit.create(Service.class);
        CharReq request1 = new CharReq("player");
        Call<Data> call=service.getallcharacters(request1);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()) {
                    ArrayList<Data.Characters> details = response.body().getcharacters();
                    if (details != null && !details.isEmpty()) {
                        txt1.setText(details.get(0).getName());
                        txt3.setText(details.get(1).getName());
                        txt5.setText(details.get(2).getName());
                        txt2.setText(details.get(0).getDesc());
                        txt4.setText(details.get(1).getDesc());
                        txt6.setText(details.get(2).getDesc());
                        String imageUrl1 = details.get(0).getimageUrl();
                        String imageUrl2 = details.get(1).getimageUrl();
                        String imageUrl3 = details.get(2).getimageUrl();
                        img1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                str=imageUrl1;
                                showoff4();
                            }
                        });
                        img2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                str=imageUrl2;
                                showoff4();
                            }
                        });
                        img3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                str=imageUrl3;
                                showoff4();
                            }
                        });
                        Picasso.get().load(imageUrl1).into(img1);
                        Picasso.get().load(imageUrl2).into(img2);
                        Picasso.get().load(imageUrl3).into(img3);

                        Log.e("api", "name:" + details.get(0).getName());
                        Log.e("api", "name:" + details.get(1).getName());
                        Log.e("api", "name:" + details.get(2).getName());


                    }
                } else {
                    Log.e("API", "Error: " + response.code() + " - " + response.message());
                    //tip.setText("Error: " + response.code());
                    Toast.makeText(MainActivity2.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                //tip.setText("itsfailure");
                Log.d("API","Error");
                Toast.makeText(MainActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    public void showoff3(){
        final Dialog dialog=new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.scoring);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<Data> call = service.getScores();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()) {
                    List<Data.Scores> scores = response.body().getScores();
                    ListView lstscores = dialog.findViewById(R.id.lstview);
                    base scoresAdapter = new base(MainActivity2.this, scores);
                    Toast.makeText(MainActivity2.this, edtt.getText().toString(), Toast.LENGTH_SHORT).show();
                    if(edtt.getText().toString().equals("")){
                        lstscores.setAdapter(scoresAdapter);
                    }

                    else{
                        ne.setname(edtt.getText().toString());
                        scores.add(ne);
                        Collections.sort(scores, new Comparator<Data.Scores>() {
                            @Override
                            public int compare(Data.Scores score1, Data.Scores score2) {
                                return Integer.compare(score2.getScore(), score1.getScore());
                            }
                        });
                        lstscores.setAdapter(scoresAdapter);
                    }
                } else {
                    Log.e("API", "Error: " + response.code() + " - " + response.message());
                    Toast.makeText(MainActivity2.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                //tip.setText("itsfailure");
                Log.d("API","Error");
                Toast.makeText(MainActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EditTextValue", edtt.getText().toString());
        editor.apply();
    }
    @Override
    protected void onResume() {
        super.onResume();
        String savedText = sharedPreferences.getString("EditTextValue", "");
        edtt.setText(savedText);
    }


}