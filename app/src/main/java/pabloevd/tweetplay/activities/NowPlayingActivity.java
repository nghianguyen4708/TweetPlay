package pabloevd.tweetplay.activities;

/**
 * Created by pablovalero on 2/26/17.
 *
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import pabloevd.tweetplay.R;
import pabloevd.tweetplay.TweetIt;
import pabloevd.tweetplay.fragments.QueueFragment;
import pabloevd.tweetplay.fragments.SongFragment;

/** TODO
 * Now playing screen, this is gonna show the controls, and use fragments to display the album art,
 * and to display the queue list
 */

public class NowPlayingActivity extends AppCompatActivity implements View.OnClickListener,
        SongFragment.OnFragmentInteractionListener, QueueFragment.OnFragmentInteractionListener{
        //,SpotifyPlayer.NotificationCallback, ConnectionStateCallback {
    private FloatingActionButton playButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private ImageButton shuffleButton;
    private ImageButton changeFragButton;
    private Config playerConfig;
    private int viewNum;
    private  int musicState =-1;
    private Player mPlayer;
    TweetIt tweetit;
    public String[] myItems ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetit = (TweetIt) getApplicationContext();
        viewNum =1;

        setContentView(R.layout.activity_nowplaying);
        final Fragment fragment = new SongFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
       // mPlayer = createPlayerInstance();
        playButton = (FloatingActionButton) findViewById(R.id.playButton);
        nextButton = (ImageButton) findViewById(R.id.playNextButton);
        changeFragButton = (ImageButton) findViewById(R.id.repeatButton);

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                //tweetit.mPlayer.pause(null);
                tweetit.mPlayer.queue(null,"spotify:track:6i0V12jOa3mr6uu4WYhUBr");
                tweetit.mPlayer.queue(null,"spotify:track:3bnVBN67NBEzedqQuWrpP4");
                tweetit.mPlayer.queue(null, "spotify:track:72Y5nO5FCZtq0w7T5JGbys");

                tweetit.mPlayer.skipToNext(null);
                //tweetit.mPlayer.resume(null);
            }

        });
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("PLAY buton was pressed");
                System.out.println("playing from party :"+myItems);
                 if(tweetit.mPlayer!= null)
                    System.out.print(mPlayer);
                if(musicState == 0) {
                   tweetit.mPlayer.pause(null);
                    musicState = 1;
                }
                else if(musicState ==1) {
                    tweetit.mPlayer.resume(null);
                    musicState = 0;
                }

                if(musicState == -1) {
                    tweetit.mPlayer.playUri(null, "spotify:track:6i0V12jOa3mr6uu4WYhUBr", 0,0);
                    tweetit.mPlayer.queue(null, "spotify:track:72Y5nO5FCZtq0w7T5JGbys");

                    musicState = 0;
                }

            }
        });

        changeFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewNum == 1) {
                    viewNum = 2;
                    final Fragment fragment2 = new QueueFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment2, fragment2.getClass().getSimpleName()).addToBackStack(null).commit();
                }
                else{
                    viewNum =1;
                    final Fragment fragment = new SongFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                }
            }
        });



    }


//    public Player createPlayerInstance(){
//
//        Config playerConfig = new Config(NowPlayingActivity.this, tweetit.token, tweetit.CLIENT_ID);
//        Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
//            @Override
//            public void onInitialized(SpotifyPlayer spotifyPlayer) {
//                mPlayer = spotifyPlayer;
//                mPlayer.addConnectionStateCallback(NowPlayingActivity.this);
//                mPlayer.addNotificationCallback(NowPlayingActivity.this);
//
//
//                //mPlayer.playUri(null,"https://open.spotify.com/track/72Y5nO5FCZtq0w7T5JGbys",0,0);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                Log.e("NowPLaying", "Could not initialize player: " + throwable.getMessage());
//            }
//        });
//        return mPlayer;
//    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
//





//      @Override
//    public void onLoggedIn() {
//
//    }
//
//    @Override
//    public void onLoggedOut() {
//
//    }
//
//    @Override
//    public void onLoginFailed(Error error) {
//
//    }
//
//    @Override
//    public void onTemporaryError() {
//
//    }
//
//    @Override
//    public void onConnectionMessage(String s) {
//
//    }
//
//    @Override
//    public void onPlaybackEvent(PlayerEvent playerEvent) {
//        Log.d("Now Playing", "Playback event received: " + playerEvent.name());
//        switch (playerEvent) {
//            // Handle event type as necessary
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onPlaybackError(Error error) {
//        Log.d("Now Playing", "Playback error received:  " + error);
//
//    }
}




