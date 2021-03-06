/*The spotify logic needs be moved out of here, and put in its own class. This should actity will
 Will show the main screen with playlists, and a now playing drawer button that can be swiped up
 to display the now playing screen.
*/

package pabloevd.tweetplay.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;
import pabloevd.tweetplay.R;
import pabloevd.tweetplay.TweetIt;
import android.widget.Button;


public class LoginActivity extends Activity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback{


    //TODO: Create list of playlist objects
    ListView playLList;
    String items;
    TweetIt tweetit;

    private static final String CLIENT_ID = "42e4cf334d044ee3b93e7dcf12a83b3f";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private Button sLoginButton;
    private ImageView spotifyLogo;
    private static final int REQUEST_CODE = 1337;
    private Player mPlayer;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TweetIt.signedIn = 1;
        tweetit = (TweetIt) getApplicationContext();
        setContentView(R.layout.login_spotify);
        spotifyLogo = (ImageView)findViewById(R.id.imageView2);
        spotifyLogo.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.spotify_logo_rgb_black));

        //sLoginButton = (Button) findViewById(R.id.sLoginButton);
//        if(sLoginButton.hasOnClickListeners()) {
            AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                    AuthenticationResponse.Type.TOKEN,
                    REDIRECT_URI);
            builder.setScopes(new String[]{"user-read-private", "streaming"});
            AuthenticationRequest request = builder.build();
            AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                tweetit.token = response.getAccessToken();
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(LoginActivity.this);
                        mPlayer.addNotificationCallback(LoginActivity.this);
                        tweetit.mPlayer = mPlayer;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        if(playerEvent.name().equals("kSpPlaybackNotifyTrackChanged")){
            System.out.println("Here we can try to emluate a queue");
        }
        if(playerEvent.name().equals("kSpPlaybackNotifyPause")){
            NowPlayingActivity.musicState = 1;

        }
        if(playerEvent.name().equals("kSpPlaybackNotifyPlay")){
            NowPlayingActivity.musicState = 0;

        }

        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
        TweetIt.signedIn = 1;
        System.out.println(TweetIt.signedIn + "IN CLASS");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onResume(){
        super.onResume();

    }



}