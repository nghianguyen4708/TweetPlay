package pabloevd.tweetplay.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import pabloevd.tweetplay.R;

/**
 * Created by pablovalero on 4/4/17.
 */

public class AppIntroActivity extends AppIntro {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("Welcome to TweetPlay", "To add songs to your queue just tweet", R.drawable.twitter_tutorial , Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("Start playing music"
                , "To begin playing music just press play, or tap on the album art to display the now playing screen"
                , R.drawable.now_playing_bar , Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("Show Queue", "Tap on the bottom left icon to navigate between queue and album art", R.drawable.queue_tut , Color.parseColor("#2196F3")));



        // Override bar/separator color.
       // setBarColor(Color.parseColor("#3F51B5"));
       // setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
//        setVibrate(true);
//        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
