package com.example.android50;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.DataManager;
import model.Photo;
import model.Tag;

public class UserAlbums extends AppCompatActivity {
    public static final int EDIT_ALBUM_CODE=1;
    public static final int CREATE_ALBUM_CODE=2;
    private static ArrayList<Album> albums;
    private RecyclerView rvAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_albums);

        try {
            albums = DataManager.load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Populate list for testing
         rvAlbums = (RecyclerView) findViewById(R.id.rvAlbums);
        AlbumAdapter adapter = new AlbumAdapter(albums);
        // Attach the adapter to the recyclerview to populate items
        rvAlbums.setAdapter(adapter);
        // Set layout manager to position the items
        rvAlbums.setLayoutManager(new LinearLayoutManager(this));

    }

    //creates menu in main screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.album_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handle menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_album:
                createAlbum();
                return true;
            case R.id.search_photos:
                searchPhotos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //creates album
    private void createAlbum(){
        Intent intent = new Intent(this, AddEditAlbum.class);
        startActivityForResult(intent, CREATE_ALBUM_CODE);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        // gather all info passed back by launched activity
        String name = bundle.getString(AddEditAlbum.ALBUM_NAME);
        int index = bundle.getInt(AddEditAlbum.ALBUM_INDEX);

        if (requestCode == EDIT_ALBUM_CODE) {
            Album album = albums.get(index);
            try {
                DataManager.editAlbum(album,name);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {

            try {
                DataManager.addAlbum(new Album(name));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //albums.add(new Album(name));
        }

        // Reflect changes on adapter
        rvAlbums.getAdapter().notifyDataSetChanged();

    }

    private void searchPhotos() {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public static ArrayList<Album> getAlbums(){
        return albums;
    }

    public static boolean dupAlbum(String name){
        for(Album a: albums){
            if(a.getAlbumName().toLowerCase().equals(name.toLowerCase())) return true;
        }
        return false;
    }

    public static void searchByTag(Tag target, ArrayList<Photo> result){
        for(Album a : albums){
            for(Photo p : a.getPhotos()){
                if(p.match(target)){
                    Log.d("test","did this run " );
                    result.add(p);
                }
            }
        }
    }

    public static void orSearchByTags(Tag t1, Tag t2, ArrayList<Photo> result){
        for(Album a : albums){
            for(Photo p : a.getPhotos()){
                if(p.match(t1) || p.match(t2)){
                    result.add(p);
                }
            }
        }
    }

    public static void andSearchByTags(Tag t1, Tag t2, ArrayList<Photo> result){
        for(Album a : albums){
            for(Photo p : a.getPhotos()){
                if(p.match(t1) && p.match(t2)){
                    result.add(p);
                }
            }
        }
    }

    /*
    private void createEditAlbum(){
        Intent intent = new Intent(this, CreateEditAlbum.class);
        startActivity(intent);
    }

     */


}