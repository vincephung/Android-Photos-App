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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.DataManager;

//handles the list of albums for home screen
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
    private ArrayList<Album> albumList;

    public AlbumAdapter(ArrayList<Album> albumList){
        this.albumList = albumList;
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.album_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(context,contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = albumList.get(position);

        TextView textView = holder.albumNameTextView;
        textView.setText(album.getAlbumName());
        /*Button delBtn = holder.deleteButton; //might need to change to del_album_button
        Button renameBtn = holder.renameButton;
        btn set text
        btn set enabled
        */
    }

    //return number of items in album list
    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView albumNameTextView;
        public Button deleteButton;
        public Button renameButton;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            albumNameTextView = (TextView) itemView.findViewById(R.id.album_name_textView);
            deleteButton = (Button) itemView.findViewById(R.id.delete_album_button);
            renameButton = (Button) itemView.findViewById(R.id.rename_album_button);

            //handle opening album when albumname is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Album curAlbum = albumList.get(position);
                        //This line is just testing to see if click works, replace it with open album later
                        Toast.makeText(context, curAlbum.getAlbumName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //handle when delete button is clicked
            deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Album curAlbum = albumList.get(position);

                        //Todo: serialiation and finalize this method
                        //albumList.remove(curAlbum);
                        try {
                            DataManager.deleteAlbum(curAlbum);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        //AlbumAdapter.this.notifyDataSetChanged();
                        notifyDataSetChanged();
                    }
                }
            });

            //handle when rename button is clicked
            renameButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Album curAlbum = albumList.get(position);

                        Bundle bundle = new Bundle();
                        bundle.putInt(AddEditAlbum.ALBUM_INDEX,position);
                        bundle.putString(AddEditAlbum.ALBUM_NAME,curAlbum.getAlbumName());
                        Intent intent = new Intent(itemView.getContext(),AddEditAlbum.class);
                        intent.putExtras(bundle);

                        Activity origin = (Activity)context;
                        origin.startActivityForResult(intent,UserAlbums.EDIT_ALBUM_CODE);

                    }
                }
            });

        }

    }


}
