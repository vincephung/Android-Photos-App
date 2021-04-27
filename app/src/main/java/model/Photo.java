package model;

import com.example.android50.UserAlbums;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Photo class is a model for photos. A photo contains a list of tags, date,
 * caption and image.
 *
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class Photo implements Serializable {
    /**
     * Eclipse generated default ID used for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * List of tags.
     */
    private ArrayList<Tag> tags;

    /**
     * The image file of the photo.
     */
    private String path;

    /**
     * List of all albums in the application, used for serialization.
     */
    private ArrayList<Album> albums = UserAlbums.getAlbums();

    /**
     * Constructor to create a new photo.
     *
     * @param path Image file of the photo.
     */
    public Photo(String path) {
        this.path = path;
        this.tags = new ArrayList<Tag>();
    }

    /**
     * Returns the image file of the photo.
     *
     * @return The image file of the photo.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Gets all of the tags of the photo.
     *
     * @return List of tags that the photo contains.
     */
    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    /**
     * Adds a tag to the photo.
     *
     * @param tag Tag to be added.
     * @return true if the add was successful.
     * @throws IOException Exception thrown if the add fails.
     */
    public boolean addTag(Tag tag) throws IOException {
        if (isDup(tag)) {
            return false;
        }
        tags.add(tag);
        DataManager.save(albums);
        return true;
    }

    /**
     * Method to remove a tag from the photo.
     *
     * @param tag Tag to be removed.
     * @throws IOException Exception thrown if the remove fails.
     */
    public void removeTag(Tag tag) throws IOException {
        tags.remove(tag);
        DataManager.save(albums);
    }

    /**
     * Method that returns true if temp is already a tag and false if temp isn't
     * already a tag
     *
     * @param temp Tag to be checked.
     * @return False if the tag is not already contained in the photo.
     */
    private boolean isDup(Tag temp) {
        for (Tag t : tags) {
            if (t.equals(temp)) {
                return true;
            }
        }
        return false;
    }

    public boolean match(Tag target){
        for(Tag t : tags){
            if(target.getName().equals(t.getName()) && t.getValue().toLowerCase().startsWith(target.getValue().toLowerCase())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Photo)) {
            return false;
        }
        return ((Photo) o).getPath().equals(path);
    }


}
