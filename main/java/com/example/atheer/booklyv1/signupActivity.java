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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsername , editTextPassword, editTextEmail,EditTextphone,EditTextpasswordCon ;
    ProgressBar Newprogressbar;
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("client");
    String name;
    String email;
    boolean flag;
    String password;
    String password2;
    String phoneNo;

    public signupActivity(){
        name=null;
        email=null;
        password=null;
        password2=null;
        phoneNo=null;
        flag=true;
    }
    public signupActivity(String name, int i) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextEmail=(EditText)findViewById(R.id.input_email);
        editTextPassword=(EditText)findViewById(R.id.input_password);
        editTextUsername=(EditText)findViewById(R.id.input_name);
        EditTextphone=(EditText)findViewById(R.id.phoneNO);
        EditTextpasswordCon=(EditText)findViewById(R.id.input_password2);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.link_login).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        Newprogressbar = (ProgressBar) findViewById(R.id.progressbar);
    }

    private void regsterUser(){
        name=editTextUsername.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        email=editTextEmail.getText().toString().trim();
        password2=EditTextpasswordCon.getText().toString().trim();
        phoneNo=EditTextphone.getText().toString().trim();
        if(phoneNo.isEmpty()){
            EditTextphone.setError("Phone number is required");
            EditTextphone.requestFocus();
            return;}

        if (phoneNo.length()<10 || phoneNo.length()>10){

            EditTextphone.setError("Phone Minimum length is 10");
            EditTextphone.requestFocus();
            return;

        }
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

        if(password2.isEmpty()){
            editTextPassword.setError("Password confirmation required");
            editTextPassword.requestFocus();
            return;
        }

        if(name.isEmpty()){
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }

        if(password2.equals(password)==false)  {
            EditTextpasswordCon.setError("Password Not matching");
            EditTextpasswordCon.requestFocus();
            return;}

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference Ref = rootRef.child("client").child("phoneNO");
        ValueEventListener eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String phoneNO2 = ds.getValue(String.class);
                    if (phoneNO2.equals(phoneNo)){
                        EditTextphone.setError("Phone number already exist");
                        EditTextphone.requestFocus();
                        flag=false;
                        return;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        Ref.addListenerForSingleValueEvent(eventListener);

        if (flag==false){
            EditTextphone.setError("Phone number already exist");
            EditTextphone.requestFocus();
            return;
        }

        Newprogressbar.setVisibility(View.VISIBLE);

        Task<AuthResult> authResultTask =mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                                            Newprogressbar.setVisibility(View.GONE);
                                                                                                                            if(task.isSuccessful()){
                                                                                                                                //  Toast.makeText(getApplicationContext(),"User Registered Successful", Toast.LENGTH_SHORT).show();
                                                                                                                                FirebaseDatabase database =  FirebaseDatabase.getInstance();
                                                                                                                                FirebaseUser user =  mAuth.getCurrentUser();
                                                                                                                                String userId = user.getUid();
                                                                                                                                DatabaseReference mRef =  database.getReference().child("client").child(userId);
                                                                                                                                mRef.child("name").setValue(name);
                                                                                                                                mRef.child("email").setValue(email);
                                                                                                                                mRef.child("phoneNO").setValue(phoneNo);
                                                                                                                                mRef.child("groupID").setValue(3);
                                                                                                                                mRef.child("totalPoint").setValue(0);
                                                                                                                                mRef.child("gender").setValue("non");
                                                                                                                                mRef.child("dateOfBIrth").setValue("00-00-0000");
                                                                                                                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                        Toast.makeText(signupActivity.this,"Verification Email Sent",Toast.LENGTH_LONG).show();
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            }
                                                                                                                            else {
                                                                                                                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                                                                                                    Toast.makeText(getApplicationContext(), "You already registered", Toast.LENGTH_LONG).show();

                                                                                                                                }

                                                                                                                                else {
                                                                                                                                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }

        );

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                regsterUser();
                break;
            case R.id.link_login:
                Intent login=new Intent(this,loginActivity.class);
                startActivity(login);
                break;

        }
    }


}

