package com.example.justovanderwerf.finalbookapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookOverviewAcivity extends AppCompatActivity {

    ListView bookListView;
    ArrayAdapter adapter;
    RequestQueue queue;
    List<String> list = new ArrayList<>();
    List<String> idList = new ArrayList<>();
    EditText searchBar;
    String url = "https://www.googleapis.com/books/v1/volumes?maxResults=40&q=";
    String newUrl;
    String query;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_overview_acivity);


        mAuth = FirebaseAuth.getInstance();
        bookListView = findViewById(R.id.bookListView);
        searchBar = findViewById(R.id.editTextBookTitle);
        queue = Volley.newRequestQueue(this);

    }

    // Make the query
    public void searchOnClick(View view) {
        query = searchBar.getText().toString();
        newUrl = url+query;


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
                Log.d("SEARCH", "SearchActivity: wrong");
            }
        });
            queue.add(stringRequest);
    }

    private void parseJSONResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray arr = obj.getJSONArray("items");

            list = new ArrayList<>();
            idList = new ArrayList<>();

            for(int i = 0; i < arr.length(); i++) {
                JSONObject volume = arr.getJSONObject(i);
                String title = volume.getJSONObject("volumeInfo").getString("title");
                Log.d("RESPONSE", "title: " + title);
                String id = volume.getString("id");
                Log.d("RESPONSE", "id: " + id);
                list.add(title);
                idList.add(id);
            }

            updateListView();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        clickListener();
        updateListView();
    }

    private void updateListView() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        bookListView.setAdapter(adapter);
    }

    private void clickListener() {
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(BookOverviewAcivity.this, BookDetailActivity.class);
                String bookId = idList.get(position);
                intent.putExtra("Id", bookId);
                startActivity(intent);
            }
        });
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
