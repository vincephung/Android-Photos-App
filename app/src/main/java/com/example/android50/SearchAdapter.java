package com.example.android50;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Photo;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private ArrayList<Photo> photoList;

    public SearchAdapter(ArrayList<Photo> photoList){
        this.photoList = photoList;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.search_item, parent, false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(context,contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        ImageView imageView = holder.search_imageview;
        /*Button delBtn = holder.deleteButton; //might need to change to del_album_button
        Button renameBtn = holder.renameButton;
        btn set text
        btn set enabled
        */
    }

    //return number of items in album list
    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView search_imageview;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            search_imageview = (ImageView) itemView.findViewById(R.id.search_imageview);



        }

    }
}
