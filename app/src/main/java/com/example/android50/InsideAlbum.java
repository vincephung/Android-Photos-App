package com.example.android50;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import model.Album;
import model.Photo;

public class InsideAlbum extends AppCompatActivity {
    private static ArrayList<Album> albums = UserAlbums.getAlbums();
    private Album curAlbum;
    private static ArrayList<Photo> photos;
    private RecyclerView rvPhotos;

    public static final String PHOTO_PATH = "photoPath";
    public static final String PHOTO_INDEX = "photoIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // activates the up arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get information from bundle and set toolbar name
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String albumName = bundle.getString(AddEditAlbum.ALBUM_NAME);
            int albumIndex = bundle.getInt(AddEditAlbum.ALBUM_INDEX);
            curAlbum = albums.get(albumIndex);
            photos = curAlbum.getPhotos();
            setTitle(albumName);
        }

        //initialize recyclerview list
        rvPhotos = (RecyclerView) findViewById(R.id.rvPhotos);
        InsideAlbumAdapter adapter = new InsideAlbumAdapter(photos);
        // Attach the adapter to the recyclerview to populate items
        rvPhotos.setAdapter(adapter);
        // Set layout manager to position the items
        rvPhotos.setLayoutManager(new LinearLayoutManager(this));
    }

    //creates menu for inside album
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inside_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handle menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPhoto:
                //TODO: finish method
                //curAlbum.addPhoto(new Photo);
                return true;
            case R.id.slideshow:
                //TODO: finish method
                //startSlideshow()
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //gets photo from file system
    private void addPhoto(){

    }
}