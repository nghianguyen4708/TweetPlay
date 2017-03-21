package pabloevd.tweetplay.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




import pabloevd.tweetplay.R;
import pabloevd.tweetplay.TweetIt;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QueueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QueueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueueFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String[] songList ;
    public String queuename;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QueueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QueueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QueueFragment newInstance(String param1, String param2) {
        QueueFragment fragment = new QueueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        ListView content = (ListView) rootView.findViewById(R.id.queueList);
//        content.addView(aboutPage);
//        setContentView(R.layout.fragment_queue);
//        String[] myItems = {"  Party1", "  Party2", "  Party3", "  Party4"}; // Build Adapter
//        ArrayAdapter<String> queueAdapter = new ArrayAdapter<String>(this, R.layout.);
//        ListView queueList = (ListView) findViewById(R.id.queueList);
//        queueList.setAdapter(queueAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_queue, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/**
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Queue to display
       // Queue queue = (Queue) this.getItem(position);
        // Check box to display which queue is selected
        // The child views in each row.
        //songList Songlist
        //queueList queueList; <
        private CheckBox checkBox;
        private TextView textView;

        // Create new songlist view
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list, null);

            // Find the child views.
            //ListView playlists = (ListView)findViewById(R.id.playLList);
            checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox);

            convertView.setTag(new QueueViewHolder(textView, checkBox));

            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    CheckBox cb = (CheckBox) v;
                    Queue queue = (Queue) cb.getTag();
                    queue.setChecked(cb.isChecked());
                }
            });
        }
        // Reuse existing row view
        else
        {
            // Because we use a ViewHolder, we avoid having to call
            // findViewById().
            QueueViewHolder viewHolder = (PlanetViewHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        // Tag the CheckBox with the Planet it is displaying, so that we can
        // access the planet in onClick() when the CheckBox is toggled.
        checkBox.setTag(queue);

        // Display planet data
        checkBox.setChecked(queue.isChecked());
        textView.setText(queue.getName());

        return convertView;
    }

}



***/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
