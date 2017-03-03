/*The spotify logic needs be moved out of here, and put in its own class. This should actity will
 Will show the main screen with playlists, and a now playing drawer button that can be swiped up
 to display the now playing screen.
*/

package pabloevd.tweetplay.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import pabloevd.tweetplay.R;


public class MainActivity extends AppCompatActivity {
    //TODO: Create list of playlist objects
    ListView playLList;
    String items;
    public static int signedIn = 0;

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        populatePlayListView();
        if(signedIn == 0) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);
        String[] myItems = {"Party1", "Party2", "Party3", "Part4"}; // Build Adapter
        ArrayAdapter<String> playListAdapter = new ArrayAdapter<String>(this, R.layout.the_playlist, myItems);
        ListView playlists = (ListView)findViewById(R.id.playLList);
        playlists.setAdapter(playListAdapter);
//        setContentView(R.layout.activity_main);
    }

    public void populatePlayListView(){
        //Create list of items

        //Build adapter

        //Set up list view

        String[] myItems = {"Party1", "Party2", "Party3", "Part4"}; // Build Adapter
        ArrayAdapter<String> playListAdapter = new ArrayAdapter<String>(this, R.layout.activity_main, myItems);
        ListView playlists = (ListView)findViewById(R.id.playLList);
        playlists.setAdapter(playListAdapter);


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