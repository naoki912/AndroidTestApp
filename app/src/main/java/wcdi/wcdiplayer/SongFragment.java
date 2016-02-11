package wcdi.wcdiplayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Comparator;

import wcdi.wcdiplayer.Items.SongObject;
import wcdi.wcdiplayer.widget.SongViewAdapter;

public class SongFragment extends ListFragment {

    private static final String EXTRA_SERIALIZABLE_SONG_OBJECTS = "song_objects";

    private AbsListView mListView;

    private SongViewAdapter mAdapter;

    private OnSongClickListener mListener;

    private ArrayList<SongObject> mSongObjectList;

    public static SongFragment newInstance(ArrayList<SongObject> songObjects) {
        SongFragment fragment = new SongFragment();

        Bundle args = new Bundle();

        args.putSerializable(EXTRA_SERIALIZABLE_SONG_OBJECTS, songObjects);

        fragment.setArguments(args);

        return fragment;
    }

    public SongFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }

        mAdapter = new SongViewAdapter(getActivity(), R.layout.song_list_item);

        mSongObjectList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mListener.onSongClick(mSongObjectList, position);

            }
        });

        if (mListView.getCount() == 0) {
            mAdapter.addAll((ArrayList<SongObject>) getArguments().getSerializable(EXTRA_SERIALIZABLE_SONG_OBJECTS));
        }

        mAdapter.sort(new Comparator<SongObject>() {
            @Override
            public int compare(SongObject lhs, SongObject rhs) {
                return lhs.mTrack - rhs.mTrack;
            }
        });

        mSongObjectList = (ArrayList<SongObject>) mAdapter.getAll();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnSongClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSongClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public interface OnSongClickListener {
        void onSongClick(ArrayList<SongObject> mSongObjectList, int position);
    }

}
