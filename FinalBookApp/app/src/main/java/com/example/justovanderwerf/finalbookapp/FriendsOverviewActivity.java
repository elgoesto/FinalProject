package com.example.justovanderwerf.finalbookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FriendsOverviewActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    FirebaseDatabase db;
    ArrayAdapter adapter;
    ListView favListView;
    List<String> idList;
    List<String> nameList;
    FirebaseAuth mAuth;
    EditText username;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends_overview);
        favListView = findViewById(R.id.favListView);
        username = findViewById(R.id.editTextFriendsName);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();




    }

    public void onFriendSearchClick(View view) {
        String searchname = username.getText().toString();

        mDatabase = db.getReference().child("username").child(searchname);



        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get uid from database and pass it to updatelistview()
                String uid = dataSnapshot.getValue(String.class);

                updateListView(uid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("search", "failed", databaseError.toException());
                // ...
            }

        };
        mDatabase.addListenerForSingleValueEvent(postListener);
    }

    private void updateListView(String uid) {
        try {
            mDatabase = db.getReference("favorites").child(uid);


            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Favorites object and set the listview.
                    Favorites fav = dataSnapshot.getValue(Favorites.class);

                    setLists(fav);

                    adapter = new ArrayAdapter(FriendsOverviewActivity.this, android.R.layout.simple_list_item_1, nameList);

                    favListView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}

            };
            mDatabase.addListenerForSingleValueEvent(postListener);
        } catch (Exception e){
            e.printStackTrace();

            Toast.makeText(FriendsOverviewActivity.this, "User niet bekend!",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void setLists(Favorites fav) {
        nameList = fav.getTitleList();
        idList = fav.getIdList();
    }
    public void homeOnClick(View view) {
        Intent intent = new Intent(this, BookOverviewAcivity.class);
        startActivity(intent);
    }

    public void overviewOnClick(View view) {
        Intent intent = new Intent(this, UsersFavoriteBooksActivity.class);
        startActivity(intent);
    }

    public void friendsOnClick(View view) {
        Intent intent = new Intent(this, FriendsOverviewActivity.class);
        startActivity(intent);
    }
}
