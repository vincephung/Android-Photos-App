package com.example.android50;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.EditText;

public class AddEditAlbum extends AppCompatActivity {
    public static final String ALBUM_NAME = "albumName";
    public static final String ALBUM_INDEX = "albumIndex";

    private EditText albumName;
    private int albumIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // activates the up arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the fields
        albumName = findViewById(R.id.edit_album_name);

        // see if info was passed in to populate fields
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            albumIndex = bundle.getInt(ALBUM_INDEX);
            albumName.setText(bundle.getString(ALBUM_NAME));
        }
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void save(View view){
        String name = albumName.getText().toString();

        if(name == null || name.length() == 0){
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialogFragment.MESSAGE_KEY,"Album Name is required");
            DialogFragment newFragment = new AlbumDialogFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }

        // make Bundle
        Bundle bundle = new Bundle();
        bundle.putInt(ALBUM_INDEX, albumIndex);
        bundle.putString(ALBUM_NAME,name);

        // send back to caller
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish(); // pops activity from the call stack, returns to parent
    }
}