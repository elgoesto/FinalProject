package com.example.justovanderwerf.finalbookapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BookDetailActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    RequestQueue queue;
    TextView textBookTitle;
    Button addToFavButton;
    ImageView imageViewCover;
    TextView textViewAuthors;
    TextView textViewDescription;
    String id;
    String newUrl;
    Book currentBook;
    Bitmap bmp;
    Favorites favorites;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        textBookTitle = findViewById(R.id.textBookTitle);
        addToFavButton = findViewById(R.id.addToFavButton);
        imageViewCover = findViewById(R.id.imageViewCover);
        textViewAuthors = findViewById(R.id.textViewAuthors);
        textViewDescription = findViewById(R.id.textViewDescription);



        queue = Volley.newRequestQueue(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        textViewDescription.setMovementMethod(new ScrollingMovementMethod());

        newBook();
    }

    /**
     * Function to get the detailed information about a book.
     */

    private void newBook(){
        Intent intent = getIntent();

        id = intent.getStringExtra("Id");

        newUrl = "https://www.googleapis.com/books/v1/volumes/"+id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Check", "onResponse: " + response);

                        parseJSONResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: Error");
            }
        });
        queue.add(stringRequest);
    }

    /**
     * JSON function to get the value from the API.
     */

    private void parseJSONResponse(String response) {

        try {
            //Store book title.
            JSONObject volume = new JSONObject(response);
            String title = volume.getJSONObject("volumeInfo").optString("title");
            String description = volume.getJSONObject("volumeInfo").optString("description");
            if(description == ""){
                description = "No description";
            }
            //Store img link.
            JSONObject imageLinks = volume.getJSONObject("volumeInfo").optJSONObject("imageLinks");
            String imageUrl = "";
            if(imageLinks != null) {
                imageUrl = imageLinks.optString("medium");
                if(imageUrl == ""){
                    imageUrl = imageLinks.optString("thumbnail");
                }
            }
            String authors = "";
            JSONArray arr = volume.getJSONObject("volumeInfo").optJSONArray("authors");

            // Store all the authors in an array.
            if(arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    authors += arr.getString(i);
                }
            }

            // Store details in a Book object.
            currentBook = new Book(title, authors, imageUrl, description, id );

            updateViews();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the details page with the retrieved data.
     */

    private void updateViews() {
        textBookTitle.setText(currentBook.getTitle());
        textViewDescription.setText(Html.fromHtml(currentBook.getDesc(), Html.FROM_HTML_MODE_COMPACT));
        textViewAuthors.setText("Authors: " + currentBook.getAuthors());

        retrieveImage();
    }

    /**
     * Function to show the image properly.
     */

    private void retrieveImage() {
        if(currentBook.getImageUrl() != "") {
            new Thread() {
                @Override
                public void run() {
                    URL url = null;
                    try {
                        url = new URL(currentBook.getImageUrl());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        setImage(bmp);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshImage();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    /**
     * Set image to the view.
     */

    private void refreshImage() {
        imageViewCover.setImageBitmap(bmp);
    }

    /**
     * In separate function, because else error when opening new image.
     */

    private void setImage(Bitmap b) {
        bmp = b;
    }

    /**
     * Add a book to favorites.
     */

    public void addToFav(View view) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        users = db.getReference("favorites").child(uid);
        Log.d("checkje", currentBook.getTitle() + currentBook.getId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favorites = dataSnapshot.getValue(Favorites.class);
                favorites.addBook(currentBook.getTitle(), currentBook.getId());
                addToDatabase(favorites);
                Intent intent = new Intent(BookDetailActivity.this, UsersFavoriteBooksActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        users.addListenerForSingleValueEvent(postListener);
    }

    /**
     * Store the selected book to the database.
     */

    private void addToDatabase(Favorites favorites) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        users = db.getReference();

        users.child("favorites").child(uid).setValue(favorites);
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

