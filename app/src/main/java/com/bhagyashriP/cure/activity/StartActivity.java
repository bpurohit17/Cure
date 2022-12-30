package com.bhagyashriP.cure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bhagyashriP.cure.R;
import com.bhagyashriP.cure.databinding.ActivityMainBinding;
import com.bhagyashriP.cure.databinding.ActivityStartBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startButton.setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this, OnboardingScreenActivity1.class));
        });
    }
}