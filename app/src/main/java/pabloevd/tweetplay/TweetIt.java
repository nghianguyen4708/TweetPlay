package pabloevd.tweetplay;

import android.app.Application;
import android.os.StrictMode;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
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

    public String jedis(){
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        String key;
        Jedis jedis = new Jedis("192.168.0.15", 6379);
        jedis.connect();
        key = jedis.lpop("queue");
        System.out.println("The Key is "+ key);
        artist = jedis.hget(key,"artist");
        System.out.println("The artist is "+ artist);
        song = jedis.hget(key,"song");
        System.out.println("The song is "+ song);
        duration = jedis.hget(key,"duration");
        System.out.println("The duration is "+ duration);
        uri = jedis.hget(key,"uri");
        System.out.println("The uri is "+ uri);


    return uri;



    }

}
