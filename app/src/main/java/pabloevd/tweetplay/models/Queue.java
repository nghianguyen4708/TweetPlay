package pabloevd.tweetplay.models;

import java.util.ArrayList;

/**
 * Created by Allen on 3/1/2017.
 */

public class Queue {
    private String name;
    private String hashtag;
    private ArrayList<Song> queue;
    private int playIndex = 0;

    public Queue(){
        name = "";
        hashtag = "";
        queue = new ArrayList<Song>();
    }


    public void playSong(int requested){
        if(requested < 0)  // Play from current index
            requested = playIndex;
        // TODO  rest of code
    }

    public void addSong(Song s){
        queue.add(s);
    }

    public void deleteSong(Song s){
        queue.remove(s);
    }


    // ************** GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
