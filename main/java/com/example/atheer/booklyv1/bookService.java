package com.example.atheer.booklyv1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class bookService extends AppCompatActivity implements View.OnClickListener {

    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;

    private DrawerLayout mDrawerLayout;

    TextView listdata;
    TextView displaytxt;
    Spinner sp;
    ArrayList<String> data;
    String service = "";
    ElegantNumberButton numRes;
    EditText date;
    DatePickerDialog datePickerDialog;
    String dateval = "";
    EditText time;
    String timeval = "";
    TimePickerDialog timePickerDialog;
    int hour;
    int minute;

    int year;
    int month;
    int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);







        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.useremail);
        navUserponts = (TextView) headerView.findViewById(R.id.userpoints);

        findViewById(R.id.buttonBook).setOnClickListener(this);

        loaduserinfo();


        mDrawerLayout = findViewById(R.id.drawerId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();

                        if (id == R.id.settingsId) {

                            startActivity(new Intent(bookService.this,settings.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(bookService.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(bookService.this,Mynavigation.class));

                        } else if (id == R.id.myservicesId){

                            startActivity(new Intent(bookService.this,myServices.class));

                        }


                        return true;
                    }
                });





        listdata = (TextView) findViewById(R.id.textlist);

        sp = (Spinner) findViewById(R.id.spinner1);
        numRes = (ElegantNumberButton) findViewById(R.id.numberRes);
        date = (EditText) findViewById(R.id.SerDate);
        time = (EditText) findViewById(R.id.SerTime);


        Intent intent = getIntent();
        listdata.setText(intent.getStringExtra("name"));
        setTitle(intent.getStringExtra("name"));





        data = new ArrayList<>();
        data.add("Book a table");
        data.add("Book a table2");



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data); //Create the Adapter to set the data
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Set the layout resource to create the drop down views.
        sp.setAdapter(adapter); //Set the data to your spinner



        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service=sp.getSelectedItem().toString();
                // displaytxt.setText(service);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        numRes.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                String counter = numRes.getNumber();



            }
        });

        date.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Calendar calander = Calendar.getInstance();
                year = calander.get(Calendar.YEAR);
                month = calander.get(Calendar.MONTH);
                day = calander.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(bookService.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        dateval = day+"-"+(month+1)+"-"+year;

                        date.setText(dateval);
                    }
                },year,month,day);

                datePickerDialog.show();

            }

        });


        time.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Calendar calander = Calendar.getInstance();
                hour = calander.get(Calendar.HOUR);
                minute = calander.get(Calendar.MINUTE);


                timePickerDialog = new TimePickerDialog(bookService.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

//                        String am_pm;
//                        if(i<12){
//                            am_pm="AM";
//                        }
//
//                        else  if (i==12){
//                            am_pm="PM";
//                        }
//
//                            else {
//                            am_pm="PM";
//                        }

                        timeval = i + ":" + i1 + " ";

                        time.setText(timeval);


                    }
                },hour,minute,false);

                timePickerDialog.show();

            }

        });






    }


//    public void dispalyResult(View view){
//        displaytxt.setText(Service);
//    }

    private void loaduserinfo() {

        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();
        ref =  database.getReference().child("client").child(userId);



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = "";
                int points = 0;


                for(DataSnapshot ds: dataSnapshot.getChildren() ){
                    name = dataSnapshot.child("name").getValue(String.class);

                    if (dataSnapshot.hasChild("totalPoint")) {
                        points = dataSnapshot.child("totalPoint").getValue(int.class);
                        navUserponts.setVisibility(View.VISIBLE);
                        navUserponts.setText(points + "");
                    }



                    navUsername.setText(name);



                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonBook:
                startActivity(new Intent(this,Booked.class));
                break;

        }
    }


}
