package com.example.atheer.booklyv1;


import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        handler.postDelayed(new Runnable() {
            Intent intent = new Intent(MainActivity.this,mHomePage.class);
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 4000);

    }
}
