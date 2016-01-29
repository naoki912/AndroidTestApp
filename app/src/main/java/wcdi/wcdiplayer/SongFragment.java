package wcdi.wcdiplayer;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import wcdi.wcdiplayer.dummy.DummyContent;
import wcdi.wcdiplayer.widget.AlbumArrayAdapter;

public class SongFragment extends ListFragment {

    public static SongFragment newInstance(String param1, String param2) {
        SongFragment fragment = new SongFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }


    private AbsListView mListView;

    private AlbumArrayAdapter mAdapter;

    public SongFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

}
