package com.example.atheer.booklyv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    EditText  editTextPassword, editTextEmail ;
    ProgressBar Newprogressbar;
    String email;
    String password;
    private DatabaseReference mDatabase;
    public loginActivity(){
        email=null;
        password=null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.Signin).setOnClickListener(this);
        findViewById(R.id.link_signup).setOnClickListener(this);
        findViewById(R.id.link_forgetPass).setOnClickListener(this);
        editTextEmail=(EditText)findViewById(R.id.EditTextemail);
        editTextPassword=(EditText)findViewById(R.id.EditTextpassword);
        mAuth=FirebaseAuth.getInstance();
        Newprogressbar = (ProgressBar) findViewById(R.id.progressbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void login(){

        password=editTextPassword.getText().toString().trim();
        email=editTextEmail.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Minimum length of password shoud be 6");
            editTextPassword.requestFocus();
            return;
        }

        Newprogressbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                   @Override
                                                                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                       Newprogressbar.setVisibility(View.GONE);
                                                                                       if(task.isSuccessful()){
                                                                                           // Read from the database

                                                                                           FirebaseDatabase database =  FirebaseDatabase.getInstance();
                                                                                           FirebaseUser user =  mAuth.getCurrentUser();
                                                                                           String userId = user.getUid();
                                                                                   //        if (user.isEmailVerified()){
                                                                                           DatabaseReference mRef =  database.getReference().child("client").child(userId);


                                                                                           if (user.isEmailVerified()){
                                                                                           mRef.addValueEventListener(new ValueEventListener() {

                                                                                               @Override
                                                                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                   int groupID=dataSnapshot.child("groupID").getValue(int.class);
                                                                                                  if(groupID==2) {
                                                                                                       Intent homepage = new Intent(loginActivity.this, mynav.class);
                                                                                                       startActivity(homepage);
                                                                                                   }
                                                                                                   else if(groupID==3){
                                                                                                       Intent homepage = new Intent(loginActivity.this, Mynavigation.class);
                                                                                                       startActivity(homepage);
                                                                                                   }
                                                                                               }

                                                                                               @Override
                                                                                               public void onCancelled(DatabaseError error) {
                                                                                                   // Failed to read value
                                                                                                   // Log.w(TAG, "Failed to read value.", error.toException());
                                                                                               }
                                                                                           });}
                                                                                           else{
                                                                                               mRef.addValueEventListener(new ValueEventListener() {

                                                                                                   @Override
                                                                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                       int groupID=dataSnapshot.child("groupID").getValue(int.class);
                                                                                                       if(groupID==1){
                                                                                                           Intent homepage = new Intent(loginActivity.this, dashboardAdmin.class);
                                                                                                           startActivity(homepage);
                                                                                                       }
                                                                                                       else Toast.makeText(getApplicationContext(), "Email not verified", Toast.LENGTH_LONG).show();
                                                                                                   }

                                                                                                   @Override
                                                                                                   public void onCancelled(DatabaseError error) {
                                                                                                       // Failed to read value
                                                                                                       // Log.w(TAG, "Failed to read value.", error.toException());
                                                                                                   }
                                                                                               });


                                                                                           }

                                                                                       }
                                                                                       else {
                                                                                           FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                                                           Toast.makeText(getApplicationContext(), "faild to sign in", Toast.LENGTH_LONG).show();
                                                                                           Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                                       }

                                                                                   }
                                                                               }

        );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Signin:
                login();
                break;
            case R.id.link_signup:
                Intent signup=new Intent(this,signupActivity.class);
                startActivity(signup);
                break;
            case R.id.link_forgetPass:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                email=editTextEmail.getText().toString().trim();
                if(email.isEmpty()){
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                    return;
                }
                else auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Email sent.", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Invalid email.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;
        }

    }
}

