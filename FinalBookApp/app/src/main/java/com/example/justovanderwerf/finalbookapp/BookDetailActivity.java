package com.example.justovanderwerf.finalbookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        newBook();
    }

    private void newBook(){
        Intent intent = getIntent();

        id = intent.getStringExtra("Id");

        newUrl = "https://www.googleapis.com/books/v1/volumes/"+id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE", "onResponse: " + response);

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

    private void parseJSONResponse(String response) {

        try {
            JSONObject volume = new JSONObject(response);
            String title = volume.getJSONObject("volumeInfo").optString("title");
            String description = volume.getJSONObject("volumeInfo").optString("description");
            if(description == ""){
                description = "No description";
            }
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

            if(arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    authors += arr.getString(i);
                }
            }

            // Save these details in a Volume object.
            currentBook = new Book(title, authors, imageUrl, description, id );

            updateViews();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        newBook();
    }


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
