package com.example.android50;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import java.util.ArrayList;

import model.Photo;
import model.Tag;

public class Search extends AppCompatActivity {

    private ArrayList<Photo> results;

    private EditText tag1Value;
    private EditText tag2Value;
    private ToggleButton tag1Type;
    private ToggleButton tag2Type;
    private RadioGroup radioGroup;
    private RecyclerView resultsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        results = new ArrayList<Photo>();

        //get fields
        tag1Value = findViewById(R.id.tag1ValueText);
        tag2Value = findViewById(R.id.tag2ValueText);
        tag1Type  = findViewById(R.id.tag1TypeBtn);
        tag2Type  = findViewById(R.id.tag2TypeBtn);
        radioGroup = findViewById(R.id.radioGroup);
        resultsRV = findViewById(R.id.resultsRV);

        SearchAdapter adapter = new SearchAdapter(results);
        resultsRV.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        resultsRV.addItemDecoration(new GridSpacing(5));
        resultsRV.setLayoutManager(layoutManager);
    }

    //creates menu in main screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handle menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    //handle clearing
    public void clear(View view){
        tag1Value.setText("");
        tag2Value.setText("");
        tag1Type.setChecked(false);
        tag2Type.setChecked(false);
        radioGroup.clearCheck();
        results.clear();
        resultsRV.getAdapter().notifyDataSetChanged();
    }

    //handle searching
    public void search(View view){
        String tag1V = tag1Value.getText().toString();
        String tag2V = tag2Value.getText().toString();
        String tag1T = tag1Type.getText().toString();
        String tag2T = tag2Type.getText().toString();
        int selectedID = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton;

        if(tag1V != null && tag1V.length() > 0 && selectedID == -1 && (tag2V == null || tag2V.length()==0)){
            Log.d("test","did this run ");
            Tag temp = new Tag(tag1T, tag1V);
            UserAlbums.searchByTag(temp, results);
            resultsRV.getAdapter().notifyDataSetChanged();
            return;
        }
        else if(tag1V != null && tag1V.length() > 0 && selectedID != -1 && tag2V != null && tag2V.length() > 0){
            selectedButton = findViewById(selectedID);
            if(selectedButton.getText().toString().equals("OR")){
                Tag t1 = new Tag(tag1T, tag1V);
                Tag t2 = new Tag(tag2T, tag2V);
                UserAlbums.orSearchByTags(t1, t2, results);
                resultsRV.getAdapter().notifyDataSetChanged();
            }
            else{
                Tag t1 = new Tag(tag1T, tag1V);
                Tag t2 = new Tag(tag2T, tag2V);
                UserAlbums.andSearchByTags(t1, t2, results);
                resultsRV.getAdapter().notifyDataSetChanged();
            }
        }
        //error check first
        else if((tag1Value.getText().toString() == null || tag1Value.getText().toString().length() == 0)){
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialogFragment.MESSAGE_KEY,"Input for First Tag is Required");
            DialogFragment newFragment = new AlbumDialogFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
        else{
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialogFragment.MESSAGE_KEY,"Invalid input");
            DialogFragment newFragment = new AlbumDialogFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
    }
}