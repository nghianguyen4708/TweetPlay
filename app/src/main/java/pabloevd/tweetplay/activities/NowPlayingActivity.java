package pabloevd.tweetplay.activities;

/**
 * Created by pablovalero on 2/26/17.
 *
 */


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
    private FloatingActionButton playButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private ImageButton shuffleButton;
    private ImageButton changeFragButton;
    public static TextView songLabel;
    public static TextView artistLabel;
    private Config playerConfig;
    private int viewNum;
    public static int musicState =-1;
    private Player mPlayer;
    TweetIt tweetit;
    public String[] myItems ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetit = (TweetIt) getApplicationContext();
        tweetit.jedisConnect();
        viewNum =1;

        setContentView(R.layout.activity_nowplaying);
        final Fragment fragment = new SongFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).commit();
        playButton = (FloatingActionButton) findViewById(R.id.playButton);
        nextButton = (ImageButton) findViewById(R.id.playNextButton);
        changeFragButton = (ImageButton) findViewById(R.id.repeatButton);
        songLabel = (TextView) findViewById(R.id.songLabel);
        artistLabel = (TextView) findViewById(R.id.artistLabel);
        if(tweetit.currentSong != null){
            songLabel.setText(tweetit.currentSong.getTitle());
            artistLabel.setText(tweetit.currentSong.getArtist());


        }

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Song next = tweetit.jedisNext();
                playSong(next);

            }

        });
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("PLAY buton was pressed");
                System.out.println("playing from party :"+myItems);
                 if(tweetit.mPlayer!= null)
                    System.out.print(mPlayer);
                else
                    System.out.println("Player is null");
                if(musicState == 0) {
                   tweetit.mPlayer.pause(null);
                    musicState = 1;
                }
                else if(musicState ==1) {
                    tweetit.mPlayer.resume(null);
                    musicState = 0;
                }

                if(musicState == -1) {
                    Song songToPlay = tweetit.jedisNext();
                    playSong(songToPlay);

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
                }
                else{
                    viewNum =1;
                    final Fragment fragment = new SongFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).commit();

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
            songLabel.setText(next.getTitle());
            artistLabel.setText(next.getArtist());
            MainActivity.miniPlayerSongLabel.setText(next.getTitle());
            tweetit.currentSong = next;
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




