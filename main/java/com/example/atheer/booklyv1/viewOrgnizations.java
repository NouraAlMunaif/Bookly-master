package com.example.atheer.booklyv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class viewOrgnizations extends AppCompatActivity {

    private static final String TAG = "viewOrgnization";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orgnizations);
        ListView list = (ListView) findViewById(R.id.mylist);
        Log.d(TAG, "onCreate: Started");



        ArrayList<String> orgs = new ArrayList<>();
        orgs.add("Urth");
        orgs.add("Five Guys");
        orgs.add("Nozomi");
        orgs.add("Lusin");
        orgs.add("Red Chilli");

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,orgs);
        list.setAdapter(adapter);

    }
}
