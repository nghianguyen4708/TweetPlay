package pabloevd.tweetplay.fragments;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import pabloevd.tweetplay.R;
import pabloevd.tweetplay.TweetIt;
import pabloevd.tweetplay.models.Song;

public class QueueFragment extends ListFragment implements OnItemClickListener{
    //private List<String> szSongList;
    private List<String> szSongList = null;

    //private OnFragmentInteractionListener mListener;

    // Constructor must be empty
    public QueueFragment() {
        TweetIt myTweetIt = new TweetIt();
        //szSongList = Arrays.asList("Song 1", "Song 2", "Song 3");
        szSongList = myTweetIt.queueList();
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            for (i = 0; i < songListSize; i++) {
//                mParam1 = getArguments().getString(ARG_PARAM1);
//                mParam2 = getArguments().getString(ARG_PARAM2);
//            }
//        }
//
//        /**
//         *
//         * populate array
//         */
//     songList.add(new Song('t',"Nghia is pretty Remix"));
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.queue_list, container, false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = new SongListAdapter(getActivity(),
                R.layout.queue_list, szSongList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public class SongListAdapter extends ArrayAdapter<String> {

        private Context context;
        private List<String> songList;

        //constructor, call on creation
        public SongListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            this.context = context;
            this.songList = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            String title = songList.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.queue_list, null);
            TextView titles = (TextView) view.findViewById(R.id.songTitles);
            titles.setText(title);
            return view;
        }
    }
}
