package pabloevd.tweetplay.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import pabloevd.tweetplay.R;
import pabloevd.tweetplay.TweetIt;

/**
 * Created by pablovalero on 3/22/17.
 */

public class PlayListActivity  extends AppCompatActivity {
    TweetIt tweetit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetit = (TweetIt) getApplicationContext();
        tweetit.jedisConnect();

        setContentView(R.layout.activity_playlist);
        List<String> myItems = tweetit.queueList();
        ArrayAdapter<String> playListAdapter = new ArrayAdapter<String>(this, R.layout.the_playlist, myItems);
        ListView playlists = (ListView)findViewById(R.id.playLList);
        playlists.setAdapter(playListAdapter);


    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

    }




    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
