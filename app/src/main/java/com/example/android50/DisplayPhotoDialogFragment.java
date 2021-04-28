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
      /*  if(getDialog() == null){
            setShowsDialog(false);
        }

       */

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
                        Album destAlbum = albums.get(which); //destination album
                        Album curAlbum = albums.get(albumIndex);
                        Photo curPhoto = curAlbum.getPhotos().get(photoIndex);

                        //handle duplicate add
                        if(curAlbum == destAlbum || destAlbum.duplicatePicture(curPhoto.getPath())){
                            Toast.makeText(getActivity().getBaseContext(),"Duplicate photo", Toast.LENGTH_SHORT).show();
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
                        InsideAlbum.getRvPhotos().getAdapter().notifyDataSetChanged();
                        getActivity().onBackPressed(); // go back to album page
                    }
                }).setNegativeButton(R.string.cancelAlbumMove, (dialog, id) -> {
                       return;
                });

        return builder.create();
    }
}
