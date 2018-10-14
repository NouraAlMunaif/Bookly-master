package com.example.atheer.booklyv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;

public class approvedAdmin extends AppCompatActivity implements View.OnClickListener{


    DatabaseReference dref;
    ListView listview;

    ArrayList<String> list=new ArrayList<>();
     ArrayAdapter<String> adapter;

    public approvedAdmin() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_admin);
        listview=(ListView)findViewById(R.id.list);
        dref= FirebaseDatabase.getInstance().getReference("client");
       // adapter.notifyDataSetChanged();
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String status;
                String name;
                String email;

                Toast.makeText(approvedAdmin.this, "ok", Toast.LENGTH_SHORT).show();
                for(DataSnapshot ds: dataSnapshot.getChildren() ){
                    if (ds.hasChild("Status")){
                        status=ds.child("Status").getValue(String.class);
                        if (status.equalsIgnoreCase("Not approved")){
                            name= ds.child("name").getValue(String.class);
                            list.add(name);
                        }
                    }
                }

                adapter= new ArrayAdapter<String>(approvedAdmin.this, android.R.layout.simple_list_item_1, list);
                listview.setAdapter(adapter);}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        }



    @Override
    public void onClick(View v) {

    }
}
