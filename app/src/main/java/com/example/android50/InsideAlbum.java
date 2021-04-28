package com.example.android50;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.Photo;

public class InsideAlbum extends AppCompatActivity {
    private static ArrayList<Album> albums = UserAlbums.getAlbums();
    private Album curAlbum;
    private static ArrayList<Photo> photos;
    private RecyclerView rvPhotos;
    private int albumIndex;

    public static final String PHOTO_PATH = "photoPath";
    public static final String PHOTO_INDEX = "photoIndex";
    public static final int REQUEST_IMAGE_GET = 1;

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
            albumIndex = bundle.getInt(AddEditAlbum.ALBUM_INDEX);
            curAlbum = albums.get(albumIndex);
            photos = curAlbum.getPhotos();
            setTitle(albumName);
        }

        //initialize recyclerview list
        rvPhotos = (RecyclerView) findViewById(R.id.rvPhotos);
        InsideAlbumAdapter adapter = new InsideAlbumAdapter(photos,curAlbum,albumIndex);
        // Attach the adapter to the recyclerview to populate items
        rvPhotos.setAdapter(adapter);
        rvPhotos.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        rvPhotos.addItemDecoration(new GridSpacing(5));
        rvPhotos.setLayoutManager(layoutManager);
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
                addPhoto();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //gets photo from file system
    private void addPhoto(){
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = intent.getData();
            String photoURI = fullPhotoUri.toString();

            if(curAlbum.duplicatePicture(photoURI)){
                Toast.makeText(getApplicationContext(),"Duplicate photo", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new photo and add to album
            try {
                curAlbum.addPhoto(new Photo(photoURI));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Reflect changes on adapter
            rvPhotos.getAdapter().notifyDataSetChanged();
        }
    }
}