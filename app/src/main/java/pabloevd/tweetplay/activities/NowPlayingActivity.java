package pabloevd.tweetplay.activities;

/**
 * Created by pablovalero on 2/26/17.
 *
 */


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

/** TODO
 * Now playing screen, this is gonna show the controls, and use fragments to display the album art,
 * and to display the queue list
 */

public class NowPlayingActivity extends AppCompatActivity implements View.OnClickListener,
        SongFragment.OnFragmentInteractionListener, QueueFragment.OnFragmentInteractionListener{
    public static FloatingActionButton playButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private ImageButton shuffleButton;
    private ImageButton changeFragButton;
    public static TextView songLabel;
    public static TextView artistLabel;
    private int viewNum;
    public static int musicState =-1;
    TweetIt tweetit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetit = (TweetIt) getApplicationContext();
        System.out.println("We enter the now playing activity");
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
        viewNum = 1;

        // where is the backend code?

        setContentView(R.layout.activity_nowplaying);
        final Fragment fragment = new SongFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).commit();
        playButton = (FloatingActionButton) findViewById(R.id.playButton);
        nextButton = (ImageButton) findViewById(R.id.playNextButton);
        prevButton = (ImageButton) findViewById(R.id.playPrevButton);
        changeFragButton = (ImageButton) findViewById(R.id.repeatButton);
        songLabel = (TextView) findViewById(R.id.songLabel);
        artistLabel = (TextView) findViewById(R.id.artistLabel);
        if(musicState == 0){
            playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_ffffff_25));
        }
        if(TweetIt.currentSong != null){
            songLabel.setText(TweetIt.currentSong.getTitle());
            artistLabel.setText(TweetIt.currentSong.getArtist());

        }

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                if(TweetIt.previousListIndex > 0){
                    TweetIt.previousListIndex--;
                    Song next = TweetIt.prevPayed.get(TweetIt.prevPayed.size()-TweetIt.previousListIndex-1);
                    playSong(next);

                }
                else if(TweetIt.previousListIndex == 0) {
                    Song next = tweetit.jedisNext();
                    playSong(next);
                }
                System.out.println(TweetIt.prevPayed);
            }

        });

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

        });        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("PLAY buton was pressed");
                 if(tweetit.mPlayer!= null) {
                     System.out.print(tweetit.mPlayer);
                     System.out.println(tweetit.queueList());
                 }
                else
                    System.out.println("Player is null");
                if(musicState == 0) {
                   tweetit.mPlayer.pause(null);
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play_arrow_white_24dp));
                    musicState = 1;
                }
                else if(musicState ==1) {
                    tweetit.mPlayer.resume(null);
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_ffffff_25));
                    musicState = 0;
                }

                if(musicState == -1) {
                    Song songToPlay = tweetit.jedisNext();
                    playSong(songToPlay);
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_ffffff_25));


                }

            }
        });

        changeFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewNum == 1) {
                    viewNum = 2;
                    final Fragment fragment2 = new QueueFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment2, fragment2.getClass().getSimpleName()).commit();
                     changeFragButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.music_record_ffffff_25));

                }
                else{
                    viewNum =1;
                    final Fragment fragment = new SongFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).commit();
                    changeFragButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.playlist_ffffff_25));

                }
            }
        });



    }


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




