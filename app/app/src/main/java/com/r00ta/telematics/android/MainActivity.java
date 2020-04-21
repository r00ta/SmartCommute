package com.r00ta.telematics.android;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_routes, R.id.navigation_trips, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        checkPermissions();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        nameUser = prefs.getString("nameUser","0");
//        passwd = prefs.getString("password","0");
//        Log.d("chupa",nameUser);
//        if(nameUser.equals("0")){
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        }

//        // Get or generate DeviceID
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String userId = prefs.getString("userId","0");
//        Log.i("userId", userId);
//
//        if(userId.equals("0")) {
//            SharedPreferences.Editor editor = prefs.edit();
//            // Edit the saved preferences
//            editor.putString("userId", userId);
//            editor.commit();
//        }


    }

    private void checkPermissions(){
        List<String> permissions = new ArrayList<>();

        // Add Storage permission if not granted
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // Add Location permissions if not granted
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

        // Request all permissions (just the needed ones)
        if (permissions.size() > 0) {
            String[] permissionArray = permissions.toArray(new String[0]);
            ActivityCompat.requestPermissions(this, permissionArray, 1);
        }
    }

}
