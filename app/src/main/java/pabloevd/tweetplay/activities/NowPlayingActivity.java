package pabloevd.tweetplay.activities;

/**
 * Created by pablovalero on 2/26/17.
 *
 */


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;

import pabloevd.tweetplay.R;
import pabloevd.tweetplay.TweetIt;
import pabloevd.tweetplay.fragments.QueueFragment;
import pabloevd.tweetplay.fragments.SongFragment;
import pabloevd.tweetplay.models.Song;
import java.util.*;

/** TODO
 * Now playing screen, this is gonna show the controls, and use fragments to display the album art,
 * and to display the queue list
 */

public class NowPlayingActivity extends AppCompatActivity implements View.OnClickListener,
        SongFragment.OnFragmentInteractionListener{//, QueueFragment.OnFragmentInteractionListener{
    public static FloatingActionButton playButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private ImageButton shuffleButton;
    private ImageButton changeFragButton;
    public static TextView songLabel;
    public static TextView artistLabel;
    private int viewNum; //Flag for fragment being displayed. 1 is showing album art, else queue
    public static int musicState =-1; //This is -1 when nothing has played. 0 When playing and 1 when paused
    TweetIt tweetit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetit = (TweetIt) getApplicationContext();

        //Try to connect to redis 5 times. Else exit
        int retryCount = 0;
        while(true) {

            try {
            tweetit.jedisConnect();
            break;
            } catch (Exception e) {
                if(retryCount > 5)
                    throw new RuntimeException("Could not connect", e);
                retryCount++;
                continue;
            }
        }

        //Set up activity view
        viewNum = 1;
        setContentView(R.layout.activity_nowplaying);
        final Fragment fragment = new SongFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).commit();
        playButton = (FloatingActionButton) findViewById(R.id.playButton);
        nextButton = (ImageButton) findViewById(R.id.playNextButton);
        prevButton = (ImageButton) findViewById(R.id.playPrevButton);
        changeFragButton = (ImageButton) findViewById(R.id.repeatButton);
        songLabel = (TextView) findViewById(R.id.songLabel);
        artistLabel = (TextView) findViewById(R.id.artistLabel);

        //Set play/pause button based on player state
        if(musicState == 0){
            playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_ffffff_25));
        }
        //Set song and arist label
        if(TweetIt.currentSong != null){
            songLabel.setText(TweetIt.currentSong.getTitle());
            artistLabel.setText(TweetIt.currentSong.getArtist());
        }
        //Next button listener and logic
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                //Check if we are playing from the previously played list of songs
                //If we are we change the index here back by 1
                if(TweetIt.previousListIndex > 0){
                    TweetIt.previousListIndex--;
                    Song next = TweetIt.prevPayed.get(TweetIt.prevPayed.size()-TweetIt.previousListIndex-1);
                    playSong(next);
                }
                //If previousListIndex is 0 means we are playing from database queue
                else if(TweetIt.previousListIndex == 0) {
                    Song next = tweetit.jedisNext();
                    playSong(next);
                }
                System.out.println(TweetIt.prevPayed);
            }

        });
        //Previous song button. Start playing from previous queue
        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                if((TweetIt.prevPayed.size()-TweetIt.previousListIndex-1)>-1) {
                    Song prev = TweetIt.prevPayed.get(TweetIt.prevPayed.size() - TweetIt.previousListIndex - 1);
                    TweetIt.previousListIndex++;
                    playSong(prev);
                }
                System.out.println(TweetIt.prevPayed);
            }
        });
        //Play button logic
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("PLAY buton was pressed");
                //Just checking if player was succesfully initialized
                 if(tweetit.mPlayer!= null) {
                     System.out.print(tweetit.mPlayer);
                     System.out.println(tweetit.queueList());
                 }
                else
                    System.out.println("Player is null");
                //If play is pressed while music is playing, then pause and change music state to 1
                if(musicState == 0) {
                   tweetit.mPlayer.pause(null);
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play_arrow_white_24dp));
                    musicState = 1;
                }
                //If music state is paused, then play and set to 0
                else if(musicState == 1) {
                    tweetit.mPlayer.resume(null);
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_ffffff_25));
                    musicState = 0;
                }
                //If music has not been initialized. Play next song in queue.
                if(musicState == -1) {
                    Song songToPlay = tweetit.jedisNext();
                    playSong(songToPlay);
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_ffffff_25));
                }

            }
        });
        //Change fragment displayed in Now Playing
        changeFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If album art is displayed change to queue list
                if(viewNum == 1) {
                    viewNum = 2;

                    final Fragment fragment2 = new QueueFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment2, fragment2.getClass().getSimpleName()).commit();
                     changeFragButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.music_record_ffffff_25));

                }
                //If queue list is being shown, then show album art
                else{
                    viewNum =1;
                    final Fragment fragment = new SongFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).commit();
                    changeFragButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.playlist_ffffff_25));
                }
            }
        });
    }

    //Logic to play song. This is called by most control buttons, and calls player.
    public void playSong(Song song){
        Song next = song;
        if(next != null) {
            String uri = next.getId();
            tweetit.mPlayer.playUri(null, uri, 0, 0);
            musicState = 0;
            playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_ffffff_25));
            songLabel.setText(next.getTitle());
            artistLabel.setText(next.getArtist());
            MainActivity.miniPlayerSongLabel.setText(next.getTitle());
            TweetIt.currentSong = next;
        }
        else{

        }

    }

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







}




