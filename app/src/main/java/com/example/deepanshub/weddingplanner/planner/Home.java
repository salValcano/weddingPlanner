package com.example.deepanshub.weddingplanner.planner;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepanshub.weddingplanner.R;
import com.example.deepanshub.weddingplanner.me.BlankFragment;
import com.example.deepanshub.weddingplanner.todoListnew.newTodo;
import com.example.deepanshub.weddingplanner.vendors.VendorFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import q.rorbin.badgeview.QBadgeView;

public class Home extends AppCompatActivity {

    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    FirebaseFirestore db;
    public void switchToFragment() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.replaceFramelayout, new BlankFragment()).commit();
    }


    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.me:

                    switchToFragment();
                    return true;
                case R.id.guestList:

                    switchToFragment();
                    return true;
                case R.id.vendor:

                    android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.replaceFramelayout, new VendorFragment()).commit();
                    return true;

                case R.id.toDoList:
                    android.support.v4.app.FragmentManager todomanager = getSupportFragmentManager();
                    todomanager.beginTransaction().replace(R.id.replaceFramelayout, new newTodo()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("Sharedpref", MODE_PRIVATE);
        editor = preferences.edit();
        setContentView(R.layout.activity_home);
        //final int[] inc = {0};
        db = FirebaseFirestore.getInstance();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        if(dbFireBase() >0)
//        {
//            setNotificationbadge(navigation,dbFireBase() );
//        }
        //dbFireBase();
        loadFireBaseCount();
        int value = preferences.getInt("Count", 0);
        if (value > 0) {
            setNotificationbadge(navigation, value);

        } else {
            setNotificationbadge(navigation, 0);
        }
        switchToFragment();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    99);
        }
    }

    public void setNotificationbadge(BottomNavigationView navigation, int datasetValue) {

        navigation.setSelectedItemId(0);
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(2);
        new QBadgeView(this).bindTarget(v).setBadgeNumber(datasetValue);
    }

    public void loadFireBaseCount() {

        db.collection("ToDoList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               final int[] inc = {0};
                                               for (DocumentSnapshot doc : task.getResult()) {

                                                   if (doc.getString("id") != null && doc.getString("id") != "") {
                                                       inc[0]++;
                                                   }
                                               }
                                               editor.putInt("Count", inc[0]);
                                               Log.e("------", "" + inc[0]);
                                               editor.commit();
                                           }

                                       }
                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Home.this, "" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }
}
