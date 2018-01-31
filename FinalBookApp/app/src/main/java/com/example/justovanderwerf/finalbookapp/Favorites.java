package com.example.justovanderwerf.finalbookapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justo van der Werf on 1/25/2018.
 *
 * Class to store favorite books to Firebase in the proper way. Else only 1 child per item in Firebase.
 */

public class Favorites {
    List<String> titleList;
    List<String> idList;



    public Favorites(){
        titleList = new ArrayList<>();
        idList = new ArrayList<>();

        // Added 1 "book" to the list, else problems with firebase. Can't add empty list.
        titleList.add("Favorite list");
        idList.add("1111111111");

    }

    public Favorites(List<String> aNameList, List<String> anIdList){
        titleList = aNameList;
        idList = anIdList;
    }

    public List<String> getTitleList(){
        return titleList;
    }

    public List<String> getIdList(){
        return idList;
    }



    public void addBook(String name, String id){
        titleList.add(name);
        idList.add(id);
    }
}