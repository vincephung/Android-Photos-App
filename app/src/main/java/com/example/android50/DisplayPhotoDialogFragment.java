package com.example.android50;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.Photo;

public class DisplayPhotoDialogFragment  extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        ArrayList<Album> albums = UserAlbums.getAlbums();
        int photoIndex = bundle.getInt(InsideAlbum.PHOTO_INDEX);
        int albumIndex = bundle.getInt(AddEditAlbum.ALBUM_INDEX);

        String [] stringAlbums = new String[albums.size()];
        for(int i = 0; i < albums.size();i++){
            stringAlbums[i] = albums.get(i).getAlbumName();
        }
        final int[] selectedIndex = new int[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.chooseAlbumAlert)
                .setItems(stringAlbums, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        selectedIndex[0] = which;
                    }
                })// Set the action buttons
                .setPositiveButton(R.string.confirmAlbumMove, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Album destAlbum = albums.get(selectedIndex[0]); //destination album
                        Album curAlbum = albums.get(albumIndex);
                        Photo curPhoto = curAlbum.getPhotos().get(photoIndex);

                        //handle duplicate add
                        if(curAlbum == destAlbum || destAlbum.duplicatePicture(curPhoto.getPath())){
                            Toast.makeText(getView().getContext(),"Duplicate photo", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            destAlbum.addPhoto(curPhoto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            curAlbum.removePhoto(curPhoto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(R.string.cancelAlbumMove, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DisplayPhotoDialogFragment.this.getDialog().cancel();
                    }
                });
        ;
        return builder.create();
    }
}
