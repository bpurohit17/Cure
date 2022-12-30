package com.bhagyashriP.cure.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bhagyashriP.cure.R;
import com.bhagyashriP.cure.databinding.ActivityOnboardingScreen1Binding;
import com.bhagyashriP.cure.util.UserModelApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class OnboardingScreenActivity1 extends AppCompatActivity {

    private ActivityOnboardingScreen1Binding binding;
    private Button btnLogin ,btnSignUp;

    // Firestore Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // redirect if user  is already login
        if (currentUser != null) {

            // go to next activity
            Intent intent = new Intent(OnboardingScreenActivity1.this, HomeActivity.class);
            startActivity(intent);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingScreen1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);


        binding.btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(OnboardingScreenActivity1.this, LoginActivity.class));
        });

        binding.btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(OnboardingScreenActivity1.this, SignupActivity.class));
        });

    }
}