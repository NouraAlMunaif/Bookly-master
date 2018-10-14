package com.example.atheer.booklyv1;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atheer.booklyv1.Category_pic;
import com.example.atheer.booklyv1.R;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category_pic> {


    public CategoryAdapter(Context context, ArrayList<Category_pic> locations) {
        super(context, 0, locations);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.album_row, parent, false);
        }

        Category_pic currentPic = getItem(position);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.title);
        nameTextView.setText(currentPic.getImage_title());
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.Image);
        imageView.setImageResource(currentPic.getImage_ID());
        // Make sure the view is visible
        imageView.setVisibility(View.VISIBLE);

        return  listItemView;

    }
}


