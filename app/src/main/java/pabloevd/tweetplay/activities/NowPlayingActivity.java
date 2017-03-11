package pabloevd.tweetplay.activities;

/**
 * Created by pablovalero on 2/26/17.
 *
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import pabloevd.tweetplay.R;
import pabloevd.tweetplay.fragments.QueueFragment;
import pabloevd.tweetplay.fragments.SongFragment;

/** TODO
 * Now playing screen, this is gonna show the controls, and use fragments to display the album art,
 * and to display the queue list
 */

public class NowPlayingActivity extends AppCompatActivity implements View.OnClickListener, SongFragment.OnFragmentInteractionListener, QueueFragment.OnFragmentInteractionListener {
    private ImageButton playButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private ImageButton shuffleButton;
    private ImageButton changeFragButton;
    private int viewNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewNum =1;
        setContentView(R.layout.activity_nowplaying);
        final Fragment fragment = new SongFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        changeFragButton = (ImageButton) findViewById(R.id.repeatButton);
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




