package com.example.justovanderwerf.finalbookapp;

/**
 * Load all the data of 1 book.
 */

public class Book {

    String title;
    String authors;
    String imageUrl;
    String description;
    String id;


    public Book(String aTitle, String anAuthors, String anImageUrl, String aDescription,String anId ){
        title = aTitle;
        authors = anAuthors;
        imageUrl = anImageUrl;
        description = aDescription;
        id = anId;


    }

    public String getTitle(){
        return title;
    }

    public String getAuthors(){
        return authors;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getDesc(){
        return description;
    }

    public String getId(){
        return id;
    }
}

