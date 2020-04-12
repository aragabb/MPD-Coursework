//Ahmed Ragab Badawy- S1804193

package com.example.infoquakes.helpers.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infoquakes.R;

//this is listadapter class for list displayed in about fragment

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;

    private final Integer[] imgid;

    public MyListAdapter(Activity context, String[] maintitle, Integer[] imgid) {
        super(context, R.layout.custom_about_list, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;  //titles like about app and about student

        this.imgid=imgid;  // icon image shown on left of about app and about student

    }

    //it gets the view from xml(custom_about_list) layout and set text and image to them.

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_about_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);


        titleText.setText(maintitle[position]);
        imageView.setImageResource(imgid[position]);


        return rowView;

    };
}