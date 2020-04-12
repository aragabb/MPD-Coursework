//Ahmed Ragab Badawy- S1804193
package com.example.infoquakes.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.infoquakes.helpers.adapters.MyListAdapter;
import com.example.infoquakes.R;

//in this class, a list listview is used to show two texts which are
//about student and about app
//MylstAdapter.class holds the adapter code
//we make instance of listadapter class and attached listview to adapter.
//list is contained in adapter.
//when user click on one of the item we check the position on which user clicked.
//if position is 0 then we know he clicked on about student
//then we start activity that is aboutstudent.class
//and so on for about app.

public class SettingsFragment extends Fragment {

   ListView list;

    String[] maintitle ={
            "About Student","About App"
    };
    Integer[] imgid={
            R.drawable.ic_about_student,R.drawable.ic_about_app
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        MyListAdapter adapter=new MyListAdapter(getActivity(), maintitle,imgid);
        list=(ListView)root.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)
                {
                    startActivity(new Intent(getActivity(), AboutStudent.class));
                }
                else
                {
                    startActivity(new Intent(getActivity(), AboutApp.class));
                }
            }
        });

        return root;
    }
}