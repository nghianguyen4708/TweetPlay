package pabloevd.tweetplay;

import android.app.Application;
import android.os.StrictMode;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.List;

import pabloevd.tweetplay.models.Queue;
import pabloevd.tweetplay.models.Song;
import redis.clients.jedis.*;

/**
 * Created by pablovalero on 3/12/17.
 */

public class TweetIt extends Application {
    public final String CLIENT_ID = "42e4cf334d044ee3b93e7dcf12a83b3f";
    public String token;
    public Player mPlayer;
    public Config playerConfig;
    public String artist;
    public String song;
    public String duration;
    public String uri;
    public String key;
    public static int previousListIndex = 0;
    public static int previousListLength;
    public static Song currentSong;
    public static List currentQueue;
    public static ArrayList<Song> prevPayed = new ArrayList<Song>();
//    Jedis jedis = new Jedis("172.24.89.81", 6379);    //School
    //Jedis jedis = new Jedis("72.190.137.46", 6379); //Sitansh
    Jedis jedis = new Jedis("172.24.92.131", 6379);  //Pablo



    //Connect redis instance
    public void jedisConnect(){
        //Override thread policy for jedis to run
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //Should use pooling later on
        //Connect
        jedis.connect();

    }

    //Get next Song object from redis database. Currently based on 'queue' key
    public Song jedisNext() {
        try{
            key = jedis.lpop("queue");
            artist = jedis.hget(key,"artist");
            song = jedis.hget(key,"song");
            duration = jedis.hget(key,"duration");
            uri = jedis.hget(key,"uri");
            Song songObj = new Song();
            songObj.setArtist(artist);
            songObj.setId(uri);
            songObj.setTitle(song);
            songObj.setDuration(duration);
            prevPayed.add(songObj);
            previousListLength = prevPayed.size();
            return songObj;
        }
        catch (Exception e){
            System.out.println("Queue is empty");
            return null;
        }

    }

    //Returns a list of the queued songs in format "SongName ArtistName"
    public List<String> queueList(){

        List<String> currentQueue = jedis.lrange("queue", 0,-1);
        String listItem;
        List<String> songlist = new ArrayList<>();

        for (String temp : currentQueue) {
            artist = jedis.hget(temp,"artist");
            song = jedis.hget(temp,"song");
            listItem = song.toString();
            songlist.add(listItem);
        }
        currentQueue = songlist;
        return songlist;
    }
}
