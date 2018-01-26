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
        users = database.getReference();

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
                            onCreated(task);

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

    private void onCreated(Task<AuthResult> task) {
        if (task.isSuccessful()) {

            // Sign in success, update UI with the signed-in user's information
            Log.d("CREATEUSER", "createUserWithEmail:success");
            FirebaseUser user = mAuth.getCurrentUser();
            makeNewFavorites(user, email);
            Intent intent = new Intent(loginActivity.this, BookOverviewAcivity.class);
            Toast.makeText(loginActivity.this, "User created.",
                    Toast.LENGTH_SHORT).show();
            startActivity(intent);

        } else {

            // If sign in fails, display a message to the user.
            Log.w("CREATEUSER", "createUserWithEmail:failure", task.getException());
            Toast.makeText(loginActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Creates a new favorites object and adds it to the database. Also adds the new user to the
     * email to uid table in the database.
     */
    private void makeNewFavorites(FirebaseUser user, String email) {
        String uid = user.getUid();
        Favorites fav = new Favorites();


        users.child("favorites").child(uid).setValue(fav);
        users.child("username").child(username).setValue(uid);
        Log.d("FAVORITES", "makeNewFavorites: ");
    }

}
