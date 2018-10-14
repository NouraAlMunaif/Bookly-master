package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class mHomePage extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_home_page);

        findViewById(R.id.btn_signIn).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.btn_signupO).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signIn:
                Intent login=new Intent(this,loginActivity.class);
                startActivity(login);
                break;
            case R.id.btn_signup:
                Intent signupc=new Intent(this,signupActivity.class);
                startActivity(signupc);
                break;
            case R.id.btn_signupO:
                Intent signupo=new Intent(this,SignUpORG.class);
                startActivity(signupo);
                break;

        }
    }
}