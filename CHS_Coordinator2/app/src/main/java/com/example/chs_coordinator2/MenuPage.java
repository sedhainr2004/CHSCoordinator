package com.example.chs_coordinator2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.chs_coordinator2.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import javax.security.auth.callback.Callback;

public class MenuPage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String uid;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        replaceFragment(new HomePage());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.menuHome:
                    replaceFragment(new HomePage());
                    break;
                case R.id.menuCalendar:
                    replaceFragment(new Calendar());
                    break;
                case R.id.menuLogout:
                    auth.signOut();
                    Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MenuPage.this, MainActivity.class));
                    break;
                case R.id.menuSchedule:
                    replaceFragment(new Schedule());
                    break;
                case R.id.menuSharePhotos:
                    replaceFragment(new PhotoSharing());
                    break;
            }
            return true;
        });



    }
    private void replaceFragment (Fragment fragment)
    {
        FragmentManager fg = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fg.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}