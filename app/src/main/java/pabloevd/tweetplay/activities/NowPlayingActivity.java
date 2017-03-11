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
import android.widget.ListView;

import pabloevd.tweetplay.R;
import pabloevd.tweetplay.fragments.SongFragment;

/** TODO
 * Now playing screen, this is gonna show the controls, and use fragments to display the album art,
 * and to display the queue list
 */

public class NowPlayingActivity extends AppCompatActivity implements View.OnClickListener, SongFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nowplaying);
        Fragment fragment = new SongFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

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




