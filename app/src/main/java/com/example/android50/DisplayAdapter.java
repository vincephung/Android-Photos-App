package com.example.android50;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.DataManager;
import model.Photo;
import model.Tag;

//handles the list of albums for home screen
public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolder> {
    private ArrayList<Tag> tagList;
    private Album crntAlbum;
    private Photo crntPhoto;

    public DisplayAdapter(ArrayList<Tag> tagList, Album crntAlbum, Photo crntPhoto) {
        this.tagList = tagList;
        this.crntAlbum = crntAlbum;
        this.crntPhoto = crntPhoto;
    }

    @Override
    public DisplayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.display_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(context, contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tag tag = tagList.get(position);

        TextView textView = holder.tagTextView;
        textView.setText(tag.toString());
        /*Button delBtn = holder.deleteButton; //might need to change to del_album_button
        Button renameBtn = holder.renameButton;
        btn set text
        btn set enabled
        */
    }

    //return number of items in album list
    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tagTextView;
        public Button deleteButton;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            tagTextView = (TextView) itemView.findViewById(R.id.tagLbl);
            deleteButton = (Button) itemView.findViewById(R.id.deleteTagBtn);


            //handle when delete button is clicked
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Tag curTag = tagList.get(position);

                        //Todo: serialiation and finalize this method
                        /*
                        //albumList.remove(curAlbum);
                        try {
                            DataManager.delete(curAlbum);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        //AlbumAdapter.this.notifyDataSetChanged();
                        notifyDataSetChanged();*/
                    }
                }
            });


        }

    }
}
