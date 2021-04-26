package com.example.android50;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import model.Album;

public class UserAlbums extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_albums);


        // Populate list for testing
         RecyclerView rvAlbums = (RecyclerView) findViewById(R.id.rvAlbums);

         ArrayList<Album> albums = new ArrayList<>();
         albums.add(new Album("album 1"));
         albums.add(new Album("album 2"));
         // Create adapter passing in the sample user data
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
                //TODO: finish method
                createEditAlbum();
                return true;
            case R.id.search_photos:
                searchPhotos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchPhotos() {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    private void createEditAlbum(){
        Intent intent = new Intent(this, CreateEditAlbum.class);
        startActivity(intent);
    }

}