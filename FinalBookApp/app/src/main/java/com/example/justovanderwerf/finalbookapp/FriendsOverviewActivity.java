package com.example.justovanderwerf.finalbookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends_overview);
        favListView = findViewById(R.id.favListView);
        username = findViewById(R.id.editTextFriendsName);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        clickListener();




    }


    /**
     * Search for User in Firebase.
     */
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

    /**
     * Update the listview with the friends favorites.
     */

    private void updateListView(String uid) {
        try {
            mDatabase = db.getReference("favorites").child(uid);


            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Favorites object from favorites class and set to the ListView.
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

            Toast.makeText(FriendsOverviewActivity.this, "User unknown!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Function to click on friends favorite book and the the details.
     */
    private void clickListener() {
        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(FriendsOverviewActivity.this, BookDetailActivity.class);
                String bookId = idList.get(position);
                intent.putExtra("Id", bookId);
                startActivity(intent);
            }
        });
    }

    /**
     * Function to get the book titles from a friend.
     */
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
