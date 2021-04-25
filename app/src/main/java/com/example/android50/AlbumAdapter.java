package com.example.android50;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Album;

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
        ViewHolder viewHolder = new ViewHolder(contactView);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView albumNameTextView;
        public Button deleteButton;
        public Button renameButton;

        public ViewHolder(View itemView) {
            super(itemView);

            albumNameTextView = (TextView) itemView.findViewById(R.id.album_name_textView);
            deleteButton = (Button) itemView.findViewById(R.id.delete_album_button);
            renameButton = (Button) itemView.findViewById(R.id.rename_album_button);
        }
    }


}
