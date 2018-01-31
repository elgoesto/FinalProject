package com.example.justovanderwerf.finalbookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class UsersFavoriteBooksActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseDatabase db;
    ArrayAdapter adapter;
    ListView favListView;
    List<String> idList;
    List<String> nameList;
    FirebaseAuth mAuth;
    String uid;

    FirebaseUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_favorite_books);

        favListView = findViewById(R.id.favListView);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        loggedInUser = mAuth.getCurrentUser();



        updateListView(uid);
        clickListener();
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

                    adapter = new ArrayAdapter(UsersFavoriteBooksActivity.this, android.R.layout.simple_list_item_1, nameList);

                    favListView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}

            };
            mDatabase.addListenerForSingleValueEvent(postListener);
        } catch (Exception e){
            e.printStackTrace();
            Log.d("FAVORITES", e.toString());
            Toast.makeText(UsersFavoriteBooksActivity.this, "er is iets aan de hand.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void clickListener() {
        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(UsersFavoriteBooksActivity.this, BookDetailActivity.class);
                String bookId = idList.get(position);
                intent.putExtra("Id", bookId);
                startActivity(intent);
            }
        });


         favListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
        int pos, long id) {
            if(uid.equals(loggedInUser.getUid())){
                deleteFavorite(pos);
                return true;
            } else {
                Toast.makeText(UsersFavoriteBooksActivity.this, "Can't delete from someone else's favorites.",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        }
    });
}

    /**
     * When long clicking an item the app the selected item will be deleted from the favorites.
     * If the last book is deleted a new book is added because firebase won't allow an
     * empty list to be saved.
     */
    private void deleteFavorite(int pos) {
        mDatabase = db.getReference();
        Favorites fav;

        nameList.remove(pos);
        idList.remove(pos);

        // if the list is empty make a new one with the starting book.
        if(idList.size() == 0){
            fav = new Favorites();
            Toast.makeText(UsersFavoriteBooksActivity.this, "Must have a favorite!",
                    Toast.LENGTH_SHORT).show();
        } else{
            fav = new Favorites(nameList, idList);
        }

        // Set the updated favorites in the database.
        mDatabase.child("favorites").child(uid).setValue(fav);
        updateListView(loggedInUser.getUid());
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
