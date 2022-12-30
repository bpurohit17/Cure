package com.bhagyashriP.cure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bhagyashriP.cure.R;
import com.bhagyashriP.cure.databinding.ActivitySignupBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private String email, password, username, fullname;
    private ProgressBar progressBar;
    private Button btnSignUp;
    private AutoCompleteTextView edtEmail;
    private EditText edtPassword, edtUserName, edtFullName;

    private FirebaseAuth firebaseAuth;

    // Firestore Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = findViewById(R.id.signup_progress);
        edtUserName = findViewById(R.id.edtUserName);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);


        firebaseAuth= FirebaseAuth.getInstance();

        binding.loginRedirectText.setOnClickListener(view ->
                startActivity(new Intent(SignupActivity.this, LoginActivity.class)));

        binding.btnSignUp.setOnClickListener(view -> {

            progressBar.setVisibility(View.VISIBLE);

            email = binding.edtEmail.getText().toString();
            password = binding.edtPassword.getText().toString();
            username =  binding.edtUserName.getText().toString();
            fullname =  binding.edtFullName.getText().toString();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(fullname)
                    || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(SignupActivity.this, "Field is required", Toast.LENGTH_SHORT).show();

            }
            else if (password.length() < 6) {
                Toast.makeText(SignupActivity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
            }
            else {
                signup(username, fullname, email, password);
            }
        });
    }

    private void signup(final String username, String fullname, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, task -> {

                    if (task.isSuccessful()) {

                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        String currentUserId = currentUser.getUid();


                        // create a user map so we can create a user in the user collection
                        Map<String, String> userObj = new HashMap<>();

                        userObj.put("userId", currentUserId);
                        userObj.put("username", username);
                        userObj.put("fullname", fullname);
                        userObj.put("imagrurl", "https://firebasestorage.googleapis.com/v0/b/cure-9ba45.appspot.com/o/placeholder.svg?alt=media&token=96a6f1cb-ffaa-4320-8562-2a9fbf129502");

                        collectionReference.add(userObj)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        progressBar.setVisibility(View.INVISIBLE);

                                        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                    }
                    else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignupActivity.this, "You are not register with this email and password", Toast.LENGTH_SHORT).show();
                    }

                });
    }
}