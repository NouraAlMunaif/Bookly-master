package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BrowseServices extends AppCompatActivity {
    private static final String TAG = "viewOrgnization";
    ArrayList<String> list;
    android.widget.ListView ListView;
    ArrayAdapter<String> adapter;
    Orgz org;
    String cat;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_services);





        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.useremail);
        navUserponts = (TextView) headerView.findViewById(R.id.userpoints);

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

                            startActivity(new Intent(BrowseServices.this,settings.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(BrowseServices.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(BrowseServices.this,Mynavigation.class));

                        } else if (id == R.id.myservicesId){

                            startActivity(new Intent(BrowseServices.this,myServices.class));

                        }


                        return true;
                    }
                });



        Intent intent = getIntent();
        cat = intent.getStringExtra("name");
        setTitle(cat);

        ListView = (ListView) findViewById(R.id.ListView);

        Log.d(TAG, "onCreate: Started");

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.org_layout,R.id.serviceInfo,list);
        org = new Orgz("Apple Pie", "4.4", "7", "Restaurant");
        Orgz org2 = new Orgz("Urth", "4.4", "7", "Restaurant");
        Orgz org3 = new Orgz("Five Guys", "4.4", "7","Restaurant");
        Orgz org4 = new Orgz("Apple Pie", "4.4", "7","Restaurant");
        Orgz org5 = new Orgz("The Cinema", "4.4", "7", "Cinema");
        Orgz org6 = new Orgz("VOX", "4.4", "7","Cinema");
        Orgz org7 = new Orgz("AMC", "4.4", "7","Cinema");


        orgBasedonCat(org2);
        orgBasedonCat(org3);
        orgBasedonCat(org4);
        orgBasedonCat(org5);
        orgBasedonCat(org6);
        orgBasedonCat(org7);

        list.add("org1");
        list.add("org2");
        list.add("org2");
        list.add("org3");
//        ArrayList<String> orgs = new ArrayList<>();
//        orgs.add("Urth");
//        orgs.add("Five Guys");
//        orgs.add("Nozomi");
//        orgs.add("Lusin");
//        orgs.add("Red Chilli");

        // adapter= new OrgzAdapter(dataModels,getApplicationContext());

        ListView.setAdapter(adapter);
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(BrowseServices.this,bookService.class);
                intent.putExtra("name",list.get(position)  );
                startActivity(intent);


            }
        });



    }

    public void orgBasedonCat( Orgz o ){
        String  s =   o.getCatg();
        if(s.equals(cat)){
            list.add(o.getName());
        }
    }

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

}
