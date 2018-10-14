package com.example.atheer.booklyv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;


public class SignUp extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressbar;
    EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        findViewById(R.id.textViewLogin).setOnClickListener(this);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);

        editTextEmail =  (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

    }

    public void regUser(){

        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("invalid email please put a valid one");
            editTextEmail.requestFocus();
            return;
        }



        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }



        if(pass.isEmpty()){

            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;

        }


        if(pass.length() <6){

            editTextPassword.setError("Password should be more the 6 charecters");
            editTextPassword.requestFocus();
            return;

        }

        progressbar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    makeText(getApplicationContext(),"user registered succesfully", LENGTH_SHORT).show();
                //    System.out.print("good");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("message");



                    myRef.setValue("Hello, World!");
                } if(!task.isSuccessful()) {

                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {

                        makeText(getApplicationContext(),"Email is already registered! Try another Email", LENGTH_SHORT).show();

                    } else {

                        makeText(getApplicationContext(), "some error happened", LENGTH_SHORT).show();
                    }
                }


            }});

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){



            case R.id.buttonSignUp:
                regUser();
                break;

            case R.id.textViewLogin:
                startActivity(new Intent(this,logIn.class));
                break;

        }}
}
