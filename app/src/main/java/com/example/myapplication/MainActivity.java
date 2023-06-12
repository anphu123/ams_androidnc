package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.fragment.CourseFragment;
import com.example.myapplication.fragment.MapsFragment;
import com.example.myapplication.fragment.NewsFragment;
import com.example.myapplication.fragment.SocialFragment;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bottom_navigation);

        FragmentManager manager = getSupportFragmentManager();
        CourseFragment courseFragment = new CourseFragment();
        manager.beginTransaction().replace(R.id.frameLayout, courseFragment).commit();

        Log.e("USERNAME",LoginActivity.USER);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = manager.beginTransaction();
                int itemId = item.getItemId();
                if (itemId == R.id.menu_Course) {
                    CourseFragment courseFragment = new CourseFragment();
                    if (num > 1) {
                        transaction.setCustomAnimations(R.anim.exit_left_to_right, R.anim.exit_left_to_right, R.anim.exit_right_to_left, R.anim.enter_right_to_left);
                    } else {
                        transaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                    }
                    transaction.replace(R.id.frameLayout, courseFragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    num = 1;
                } else if (itemId == R.id.menu_Maps) {
                    MapsFragment mapsFragment = new MapsFragment();
                    if (num > 2) {
                        transaction.setCustomAnimations(R.anim.exit_left_to_right, R.anim.exit_left_to_right, R.anim.exit_right_to_left, R.anim.enter_right_to_left);
                    } else {
                        transaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                    }
                    transaction.replace(R.id.frameLayout, mapsFragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    num = 2;
                } else if (itemId == R.id.menu_News) {
                    NewsFragment newsFragment = new NewsFragment();
                    if (num > 3) {
                        transaction.setCustomAnimations(R.anim.exit_left_to_right, R.anim.exit_left_to_right, R.anim.exit_right_to_left, R.anim.enter_right_to_left);
                    } else {
                        transaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                    }
                    transaction.replace(R.id.frameLayout, newsFragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    num = 3;
                } else if (itemId == R.id.menu_Social) {
                    SocialFragment socialFragment = new SocialFragment();
                    manager.beginTransaction()
                            .setCustomAnimations(
                                    R.anim.enter_right_to_left,
                                    R.anim.exit_right_to_left,
                                    R.anim.enter_left_to_right,
                                    R.anim.exit_left_to_right
                            )
                            .replace(R.id.frameLayout, socialFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                    num = 4;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn muốn đăng xuất?")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                        overridePendingTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit);
                        LoginManager.getInstance().logOut();
                    }
                })
                .setNegativeButton("huỷ bỏ", null)
                .show();
    }
}