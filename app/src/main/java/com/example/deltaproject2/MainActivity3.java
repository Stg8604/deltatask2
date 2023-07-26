package com.example.deltaproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity3 extends AppCompatActivity {
    private TextView tip;
    private String edtname;
    private static final String url="https://api-obstacle-dodge.vercel.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        tip=findViewById(R.id.tip3);
        getSupportActionBar().hide();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<Data> call = service.getService();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()) {
                    Data data = response.body();
                    String message=data.getTip();
                    Log.d("API", "Response: " + message);
                    tip.setText(message);
                } else {
                    Log.e("API", "Error: " + response.code() + " - " + response.message());
                    tip.setText("Error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                tip.setText("itsfailure");
                Log.d("API","Error");
            }
    });
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                startActivity(intent);
            }
        }, 8000);
    }

}