package model;

import android.util.Log;

import com.example.android50.UserAlbums;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

//class to help manage data and implement serialize methods such as save and load
public class DataManager implements Serializable {
    /**
     * String for the data directory
     */
    private static final String storeDir = "/data/data/com.example.android50/files";

    /**
     * String for where the serialization data is stored. Stores the data for all
     * albums.
     */
    private static final String storeFile = "albums.dat";

    /**
     * Array list to hold all albums
     */
    private static ArrayList<Album> albums;

    /**
     * Method to save data for all albums into data/albums.dat file. Used for
     * serialization.
     *
     * @param albums ArrayList that contains the list of all albums in the
     *                 application.
     * @throws IOException Exception thrown when serialization goes wrong
     *                     (writeObject).
     */
    public static void save(ArrayList<Album> albums) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(albums);
        oos.close();
    }

    /**
     * Method to load data for all albums from the data/albums.dat file. Used for
     * serialization.
     *
     * @return List of all albums that were saved within the file.
     * @throws IOException            Exception thrown when serialization goes wrong
     *                                (readObject).
     * @throws ClassNotFoundException Exception thrown when file cannot be
     *                                found/filepath is incorrect.
     */
    public static ArrayList<Album> load() throws IOException, ClassNotFoundException {
        if (fileEmpty()) {
            albums = new ArrayList<Album>();
            return albums; // handle empty read
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
        ArrayList<Album> albumList = (ArrayList<Album>) ois.readObject();
        ois.close();
        return albumList;
    }

    /**
     * Determines whether the albums.dat file is empty.
     *
     * @return True if the file is albums.dat file is empty.
     */
    private static boolean fileEmpty() throws IOException {
        File file = new File(storeDir + File.separator + storeFile);
        File dirPath = new File(storeDir);
        //creates a new file if it doesnt exist
        if(!dirPath.exists()){
            dirPath.mkdir();
            file.createNewFile();
        }
        return file.length() == 0;
    }

    public static void deleteAlbum(Album album) throws IOException, ClassNotFoundException {
        //albums = load();
        albums = UserAlbums.getAlbums();
        albums.remove(album);
        save(albums);
    }

    public static void addAlbum(Album album) throws IOException, ClassNotFoundException {
        //albums = load();
        albums = UserAlbums.getAlbums();
        albums.add(album);
        save(albums);
    }

    public static void editAlbum(Album album, String name) throws IOException, ClassNotFoundException {
        album.setAlbumName(name);
        save(albums);
    }

}
