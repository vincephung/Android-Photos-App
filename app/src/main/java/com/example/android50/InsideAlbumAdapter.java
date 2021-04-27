package com.example.android50;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.Photo;

public class InsideAlbumAdapter extends RecyclerView.Adapter<InsideAlbumAdapter.ViewHolder> {
    private ArrayList<Photo> photoList;
    private Album curAlbum;

    public InsideAlbumAdapter(ArrayList<Photo> photoList, Album curAlbum){
        this.photoList = photoList;
        this.curAlbum = curAlbum;

    }

    @Override
    public InsideAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.photo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(context,contactView);
        return viewHolder;
    }

    // Sets all of the imageviews to the correct image
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        ImageView imageView = holder.photo_imageView;
        Uri imageURI = Uri.parse(photo.getPath());
        Log.d("test","did this run " + imageURI);
        imageView.setImageURI(imageURI);
    }


    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo_imageView;
        public Button deleteButton;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            photo_imageView = (ImageView) itemView.findViewById(R.id.photo_imageView);
            deleteButton = (Button) itemView.findViewById(R.id.delete_photo_button);

            //handle opening a picture
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Photo curPhoto = photoList.get(position);

                        Bundle bundle = new Bundle();
                        bundle.putInt(InsideAlbum.PHOTO_INDEX,position);
                        bundle.putString(InsideAlbum.PHOTO_PATH,curPhoto.getPath().toString());
                        Intent intent = new Intent(itemView.getContext(),DisplayPhoto.class);
                        intent.putExtras(bundle);

                        Activity origin = (Activity)context;
                        origin.startActivity(intent);
                    }
                }
            });

            //handle when delete button is clicked
            deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Photo curPhoto = photoList.get(position);
                        try {
                            curAlbum.removePhoto(curPhoto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        notifyDataSetChanged();
                    }
                }
            });


        }
    }
}
