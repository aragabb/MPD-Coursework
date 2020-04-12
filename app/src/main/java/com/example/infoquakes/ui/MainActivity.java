//Ahmed Ragab Badawy- S1804193
package com.example.infoquakes.ui;

import android.os.Bundle;

import com.example.infoquakes.R;
import com.example.infoquakes.ui.timeline.OnBackPressed;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    //this is main class that sets the navigation bottom and displays 4 items
    //that are timeline,map,filter and settings

    protected OnBackPressed onBackPressedListener;
    public static boolean backFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_timeline, R.id.navigation_map,R.id.navigation_filter, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            if(!backFlag) {
                onBackPressedListener.onBackPressed(); }
            else {
                super.onBackPressed(); }
        else
            super.onBackPressed();
    }

    public void setOnBackPressedListener(OnBackPressed onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

}
