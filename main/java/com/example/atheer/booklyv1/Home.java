package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayList<Category_pic> createLists = new ArrayList<>();
        Category_pic restaurant = new Category_pic("Restaurant", R.drawable.restaurant);
        Category_pic clinic = new Category_pic("Clinic", R.drawable.clinic);
        Category_pic salon = new Category_pic("Salon", R.drawable.salon);
        Category_pic cinema = new Category_pic("Cinema", R.drawable.cinema);
        Category_pic event = new Category_pic("Events", R.drawable.event);
        Category_pic meeting = new Category_pic("Meeting rooms", R.drawable.meeting);

//
        createLists.add(restaurant);
        createLists.add(clinic);
        createLists.add(salon);
        createLists.add(cinema);
        createLists.add(event);
        createLists.add(meeting);

        CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(), createLists);
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {


                //
                Category_pic value = ( Category_pic )adapter.getItemAtPosition(position);  ;//getter method
                String title=value.getImage_title();
                // Toast.makeText(Home.this, title, Toast.LENGTH_LONG).show();
                //     Intent it = new Intent(Home.this,Product_List.class);
                //     it.putExtra("name", title);
                //  startActivity(it);

            }


        });



    }



}
