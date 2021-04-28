package com.example.android50;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import model.Album;
import model.Photo;
import model.Tag;

public class DisplayPhoto extends AppCompatActivity {
    private static final String ALBUM_NAME = "albumName";
    private static final String ALBUM_INDEX = "albumIndex";
    private static final String PHOTO_INDEX = "photoIndex";

    private RecyclerView rvTags;
    private ImageView img;
    private Album crntAlbum;
    private Photo crntPhoto;
    private ArrayList<Tag> tagsList;
    private int albumIndex;
    private int photoIndex;
    private String albumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // activates the up arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get information from bundle and set toolbar name
        Bundle bundle = getIntent().getExtras();
        Log.d("test","rbefore");

        if(bundle!=null){
            Log.d("test","reached here");
            albumName = bundle.getString(AddEditAlbum.ALBUM_NAME);
            albumIndex = bundle.getInt(AddEditAlbum.ALBUM_INDEX);
            photoIndex = bundle.getInt(InsideAlbum.PHOTO_INDEX);
            crntAlbum = UserAlbums.getAlbums().get(albumIndex);
            crntPhoto = crntAlbum.getPhotos().get(photoIndex);
            tagsList = crntPhoto.getTags();
            setTitle(albumName);
        }

        // Populate tag list
        rvTags = (RecyclerView) findViewById(R.id.rvTags);

       // DisplayAdapter adapter = new DisplayAdapter(tagsList, crntAlbum, crntPhoto);
        DisplayAdapter adapter = new DisplayAdapter(crntPhoto);

        // Attach the adapter to the recyclerview to populate items
        rvTags.setAdapter(adapter);
        // Set layout manager to position the items
        rvTags.setLayoutManager(new LinearLayoutManager(this));


        //set imageview
        img = findViewById(R.id.display_imageview);
        Uri imageURI = Uri.parse(crntPhoto.getPath());
        img.setImageURI(imageURI);

    }


    //creates menu for display photo
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handle menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tag_add:
                Bundle bundleAdd = new Bundle();
                bundleAdd.putInt(ALBUM_INDEX, albumIndex);
                bundleAdd.putInt(PHOTO_INDEX, photoIndex);
                bundleAdd.putString(ALBUM_NAME, albumName);
                Intent intent = new Intent(this, AddTag.class);
                intent.putExtras(bundleAdd);
                startActivity(intent);
                return true;
            case R.id.move_photo:
                Bundle bundleMove = new Bundle();
                DialogFragment newFragment = new DisplayPhotoDialogFragment();
                newFragment.setArguments(bundleMove);
                newFragment.getDialog().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode != RESULT_OK){
            return;
        }
        else{
            rvTags.getAdapter().notifyDataSetChanged();
        }
    }

}