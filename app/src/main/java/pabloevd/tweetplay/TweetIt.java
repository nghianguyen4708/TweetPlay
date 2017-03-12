package pabloevd.tweetplay;

import android.app.Application;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;

/**
 * Created by pablovalero on 3/12/17.
 */

public class TweetIt extends Application {
    public final String CLIENT_ID = "42e4cf334d044ee3b93e7dcf12a83b3f";
    public String token;
    public Player mPlayer;
    public Config playerConfig;

}
