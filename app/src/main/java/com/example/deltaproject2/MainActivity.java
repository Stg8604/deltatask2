package com.example.deltaproject2;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        LinearLayout container = findViewById(R.id.container);
        CustomView movingObstaclesView = new CustomView(this, null);
        container.addView(movingObstaclesView);
        movingObstaclesView.startMoving();
    }

}