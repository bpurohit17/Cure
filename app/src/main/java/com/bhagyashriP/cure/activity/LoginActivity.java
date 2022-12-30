package com.bhagyashriP.cure.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.bhagyashriP.cure.databinding.ActivityLoginBinding;
import com.bhagyashriP.cure.util.UserModelApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private ProgressBar progressBar;
    private Button btnLogin;
    private AutoCompleteTextView edtEmail;
    private EditText edtPassword;

    private FirebaseAuth firebaseAuth;
    // Firestore Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnLogin = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.signup_progress);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);


        firebaseAuth= FirebaseAuth.getInstance();


        binding.signupRedirectText.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });

//        binding.txtForgetPassword.setOnClickListener(view -> {
//            startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
//        });

        binding.btnLogin.setOnClickListener(view -> {

//            progressBar.setVisibility(View.VISIBLE);

            String email = binding.edtEmail.getText().toString();
            String password = binding.edtPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Field is required", Toast.LENGTH_SHORT).show();
            }
            else {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            String currentUserId = currentUser.getUid();


                            collectionReference
                                    .whereEqualTo("userId", currentUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                                    if (error != null) {

                                    }
                                    if(!queryDocumentSnapshots.isEmpty()) {
//                                        progressBar.setVisibility(View.INVISIBLE);

                                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {

                                            UserModelApi userModelApi = UserModelApi.getInstance(); // Global API
                                            userModelApi.setUsername(snapshot.getString("username"));
                                            userModelApi.setUserId(snapshot.getString("userId"));

                                            // go to next activity
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }
                            });


                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

//    private void loginEmailPasswordUser(String email, String pwd) {
//        progressBar.setVisibility(View.VISIBLE);
//
//        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)) {
//
//            firebaseAuth.signInWithEmailAndPassword(email, pwd)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            assert currentUser != null;
//                            final String currentUserId = currentUser.getUid();
//
//                            collectionReference
//                                    .whereEqualTo("userId", currentUserId)
//                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
//                                                            @Nullable FirebaseFirestoreException error) {
//                                            if (error != null) {
//
//                                            }
//                                            assert queryDocumentSnapshots != null;
//                                            if (!queryDocumentSnapshots.isEmpty()) {
//                                                progressBar.setVisibility(View.INVISIBLE);
//
//                                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
//
//                                                    UserModelApi userModelApi = UserModelApi.getInstance(); // Global API
//                                                    userModelApi.setUsername(snapshot.getString("username"));
//                                                    userModelApi.setUserId(snapshot.getString("userId"));
//
//                                                    // go to next activity
//                                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                                    startActivity(intent);
//                                                }
//                                            }
//                                        }
//                                    });
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressBar.setVisibility(View.INVISIBLE);
//
//                        }
//                    });
//
//        } else {
//            progressBar.setVisibility(View.INVISIBLE);
//            Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_LONG).show();
//        }
//    }
}