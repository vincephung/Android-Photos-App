package com.example.android50;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.io.IOException;

import model.Photo;
import model.Tag;

public class AddTag extends AppCompatActivity {
    private final String ALBUM_NAME = "albumName";
    private final String ALBUM_INDEX = "albumIndex";
    private final String PHOTO_INDEX = "photoIndex";

    private Photo crnt;
    private String albumName;
    private int photoIndex;
    private int albumIndex;

    private EditText tagValue;
    private ToggleButton tagType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        //get fields
        tagType = findViewById(R.id.typeBtn);
        tagValue = findViewById(R.id.tag_value);

        // see if info was passed in to populate fields
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            albumName = bundle.getString(ALBUM_NAME);
            photoIndex = bundle.getInt(PHOTO_INDEX);
            albumIndex = bundle.getInt(ALBUM_INDEX);
            crnt = UserAlbums.getAlbums().get(albumIndex).getPhotos().get(photoIndex);
            setTitle(albumName);
        }
    }

    //method to handle pressing cancel btn
    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    //method to handle pressing add btn
    public void add(View view){
        String type = tagType.getText().toString();
        String value = tagValue.getText().toString();
        Tag temp = new Tag(type, value);

        if(value == null || value.length() == 0){
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialogFragment.MESSAGE_KEY,"Tag value is required");
            DialogFragment newFragment = new AlbumDialogFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
        try {
            if(!crnt.addTag(temp)){
                Bundle bundle = new Bundle();
                bundle.putString(AlbumDialogFragment.MESSAGE_KEY,"Tag Combination Already Exists");
                DialogFragment newFragment = new AlbumDialogFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "badfields");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // send back to caller
        setResult(RESULT_OK);
        finish(); // pops activity from the call stack, returns to parent
    }
}