package wcdi.wcdiplayer;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import wcdi.wcdiplayer.dummy.DummyContent;
import wcdi.common.widget.GenericArrayAdapter;
import wcdi.wcdiplayer.widget.AlbumArrayAdapter;

public class AlbumFragment extends Fragment implements AbsListView.OnItemClickListener {

    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    private AbsListView mListView;

    private AlbumArrayAdapter mAdapter;

    public AlbumFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }

        mAdapter = new AlbumArrayAdapter(getActivity(), R.layout.album_list_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

}
