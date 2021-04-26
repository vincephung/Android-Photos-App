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
    public static final int EDIT_ALBUM_CODE=1;
    public static final int CREATE_ALBUM_CODE=2;
    private ArrayList<Album> albums;
    private RecyclerView rvAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_albums);

        // Populate list for testing
         rvAlbums = (RecyclerView) findViewById(R.id.rvAlbums);

         albums = new ArrayList<>();
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
                createAlbum();
                return true;
            case R.id.search_photos:
                //TODO: finish method
               // searchPhotos();
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
            //todo: serialize
            Album album = albums.get(index);
            album.setAlbumName(name);
        } else {
            //todo: serialize
            albums.add(new Album(name));
        }

        // Reflect changes on adapter
        rvAlbums.getAdapter().notifyDataSetChanged();

    }
}