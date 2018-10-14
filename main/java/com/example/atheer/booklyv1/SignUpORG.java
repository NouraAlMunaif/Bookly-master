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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpORG extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsername , editTextPassword, editTextEmail,EditTextphone,EditTextpasswordCon, EditTextphoneNo ;
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
    String recordNO;

    public SignUpORG(){
        name=null;
        email=null;
        password=null;
        password2=null;
        recordNO=null;
        flag=true;
        phoneNo=null;
    }
    public SignUpORG(String name, int i) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_org);
        editTextEmail=(EditText)findViewById(R.id.input_email);
        editTextPassword=(EditText)findViewById(R.id.input_password);
        editTextUsername=(EditText)findViewById(R.id.input_name);
        EditTextphone=(EditText)findViewById(R.id.recordNO);
        EditTextpasswordCon=(EditText)findViewById(R.id.input_password2);
        EditTextphoneNo=(EditText)findViewById(R.id.Phone);
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
        recordNO=EditTextphone.getText().toString().trim();
        phoneNo=EditTextphoneNo.getText().toString().trim();

        if (phoneNo.length()<10 || phoneNo.length()>10){

            EditTextphone.setError("Phone Minimum length is 10");
            EditTextphone.requestFocus();
            return;

        }
        if(recordNO.isEmpty()){
            EditTextphone.setError("Record number is required");
            EditTextphone.requestFocus();
            return;}

        if (recordNO.length()<10 || recordNO.length()>10){

            EditTextphone.setError("Record Number Minimum length is 10");
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


        Newprogressbar.setVisibility(View.VISIBLE);

        Task<AuthResult> authResultTask =mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                                            Newprogressbar.setVisibility(View.GONE);
                                                                                                                            if(task.isSuccessful()){
                                                                                                                                // Toast.makeText(getApplicationContext(),"User Registered Successful", Toast.LENGTH_SHORT).show();
                                                                                                                                FirebaseDatabase database =  FirebaseDatabase.getInstance();
                                                                                                                                FirebaseUser user =  mAuth.getCurrentUser();
                                                                                                                                String userId = user.getUid();
                                                                                                                                DatabaseReference mRef =  database.getReference().child("client").child(userId);
                                                                                                                                mRef.child("name").setValue(name);
                                                                                                                                mRef.child("email").setValue(email);
                                                                                                                                mRef.child("recordNO").setValue(recordNO);
                                                                                                                                mRef.child("groupID").setValue(2);
                                                                                                                                mRef.child("phoneNO").setValue(phoneNo);
                                                                                                                                mRef.child("Status").setValue("Not approved");
                                                                                                                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                        Toast.makeText(SignUpORG.this,"Verification Email Sent",Toast.LENGTH_LONG).show();
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

