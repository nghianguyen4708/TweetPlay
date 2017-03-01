package Models;

/**
 * Created by Allen on 3/1/2017.
 */

public class Song {
    private String id;
    private String title;
    private String artist;

    public Song(){
        id = "";
        title = "";
        artist = "";
    }

    public Song( char type, String x){
        if (type == 't'){
            title = x;
        } else if (type == 'a'){
            artist = x;
        } else if (type == 'i') {
            id = x;
        }
        getData();
    }

    private void getData(){
        if(id.equals("")){
            // TODO
        }else if(title.equals("")){
            // TODO
        } else {
            // TODO
        }
    }

    @Override
    public String toString(){
        return (title + " by " + artist);
    }

    // ************* GETTERS AND SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
