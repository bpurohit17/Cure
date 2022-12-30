package com.bhagyashriP.cure.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.bhagyashriP.cure.R;
import com.bhagyashriP.cure.databinding.ActivityHomeBinding;
import com.bhagyashriP.cure.fragment.ChatFragment;
import com.bhagyashriP.cure.fragment.HomeFragment;
import com.bhagyashriP.cure.fragment.ScheduleFragment;
import com.bhagyashriP.cure.fragment.ProfileFragment;
import com.bhagyashriP.cure.util.UserModelApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        UserModelApi.getInstance().getUsername();

        if (bundle != null) {
            String username = bundle.getString("username");
            String userId = bundle.getString("userId");

        }

        // BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.action_home)
                    fragment = new HomeFragment();

                else if (item.getItemId() == R.id.action_schedule)
                    fragment = new ScheduleFragment();

                else if (item.getItemId() == R.id.action_chat)
                    fragment = new ChatFragment();

                else if (item.getItemId() == R.id.action_profile)
                    fragment = new ProfileFragment();

                return loadFragment(fragment);
            }

            private boolean loadFragment(Fragment fragment) {
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);


//        binding.txtForgetPassword.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, ForgetActivity.class)));
//        binding.btnLogin.setOnClickListener(view -> {
//
//        });
    }
}