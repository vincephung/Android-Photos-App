package com.example.android50;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
}