package com.example.justovanderwerf.finalbookapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loginActivity extends AppCompatActivity {

    EditText usernameEdittext, passwordEdittext, emailEdittext;
    Button loginButton, registerButton;
    String email, password, username;
    FirebaseDatabase database;
    DatabaseReference users;

    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");

        usernameEdittext = findViewById(R.id.usernameEditText);
        emailEdittext = findViewById(R.id.emailEditText);
        passwordEdittext = findViewById(R.id.passwordEditText);

        loginButton = findViewById(R.id.buttonLogin);
        registerButton = findViewById(R.id.buttonRegister);


    }

    public void createUser(View view){
        email = emailEdittext.getText().toString();
        password = passwordEdittext.getText().toString();
        username = usernameEdittext.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("succeslogin", "createUserWithEmail:success");
                            String mail = email.replace('.',',');
                            String uid = mAuth.getCurrentUser().getUid();
                            DatabaseReference ref = database.getReference();
                            if (username != ""){
                                ref.child("users").child(username).child(username).setValue(username);
                            }

                            //ref.child("users").child(username).child(email).setValue(email); // dit doet het niet.

                            Intent intent = new Intent(loginActivity.this, BookOverviewAcivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("faillogin", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(loginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }




    public void LogIn(View view) {
        email = emailEdittext.getText().toString();
        password = passwordEdittext.getText().toString();
        username = usernameEdittext.getText().toString();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("succes", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(loginActivity.this, BookOverviewAcivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("failed", "signInWithEmail:failure", task.getException());
                            Toast.makeText(loginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
