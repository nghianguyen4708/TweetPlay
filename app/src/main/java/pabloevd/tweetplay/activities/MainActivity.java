/*The spotify logic needs be moved out of here, and put in its own class. This should actity will
 Will show the main screen with playlists, and a now playing drawer button that can be swiped up
 to display the now playing screen.
*/

package pabloevd.tweetplay.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import pabloevd.tweetplay.R;
import pabloevd.tweetplay.TweetIt;
import pabloevd.tweetplay.models.Song;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //TODO: Create list of playlist objects
    private ImageButton nowPlayingButton;
    private ImageButton playButton;
    public static TextView miniPlayerSongLabel;
    ListView playLList;
    String items;
    TweetIt tweetit;
    public static int signedIn = 0;
    public String[] myItems;
    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetit = (TweetIt) getApplicationContext();
        //Call login activity if app was just opened.
        if(TweetIt.signedIn == 0) {
            System.out.println(TweetIt.signedIn + " FIRST");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            startActivity(new Intent(MainActivity.this, AppIntroActivity.class));
            System.out.println(TweetIt.signedIn + " LAST");
            finish();
        }

        setContentView(R.layout.activity_main);
        String[] myItems = {"   TweetIt Session"}; // Build Adapter
        ArrayAdapter<String> playListAdapter = new ArrayAdapter<String>(this, R.layout.the_playlist, myItems);
        ListView playlists = (ListView)findViewById(R.id.playLList);
        playlists.setAdapter(playListAdapter);
        playButton = (ImageButton) findViewById(R.id.playButton);
        miniPlayerSongLabel = (TextView) findViewById(R.id.textView3);
        nowPlayingButton = (ImageButton) findViewById(R.id.nowPlayingButton);
        playlists.setOnItemClickListener(onItemClickListener);



        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("PLAY button was pressed");
                //System.out.println("playing from party :"+myItems);
                if(tweetit.mPlayer != null)
                    System.out.print(tweetit.mPlayer);
                else
                    System.out.println("Player is null");
                if(NowPlayingActivity.musicState == 0) {
                    tweetit.mPlayer.pause(null);
                    NowPlayingActivity.musicState = 1;
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.play_000000_25));


                }
                else if(NowPlayingActivity.musicState == 1) {
                    tweetit.mPlayer.resume(null);
                    NowPlayingActivity.musicState = 0;
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_black_000000_25));

                }
                else if(NowPlayingActivity.musicState == -1) {
                    tweetit.jedisConnect();
                    Song next = tweetit.jedisNext();
                    if(next != null) {
                        String uri = next.getId();
                        tweetit.mPlayer.playUri(null, uri, 0, 0);
                        NowPlayingActivity.musicState = 0;
                        MainActivity.miniPlayerSongLabel.setText(next.getTitle());
                        playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_black_000000_25));
                        tweetit.currentSong = next;
                    }
                    else{
                    }
                }
            }
            });

        nowPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NowPlayingActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

    }

    @Override
    protected void onResume()
    {
        if(NowPlayingActivity.musicState == 0) {
            playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_black_000000_25));

        }
        else if(NowPlayingActivity.musicState == 1) {
            playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.play_000000_25));

        }
        else if(NowPlayingActivity.musicState == -1) {
            playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.play_000000_25));
        }
        super.onResume();
    }


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            // TODO Auto-generated method stub
            startActivity(new Intent(MainActivity.this, PlayListActivity.class));

            //do your job here, position is the item position in ListView
        }
    };


    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs

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
}