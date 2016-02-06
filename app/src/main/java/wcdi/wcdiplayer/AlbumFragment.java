package wcdi.wcdiplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import wcdi.wcdiplayer.widget.AlbumViewAdapter;


public class AlbumFragment extends Fragment {

    private AlbumViewAdapter mAdapter;

    private AbsListView mListView;

    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }

        mAdapter = new AlbumViewAdapter(getActivity(), R.layout.album_list_item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_directory_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, SongFragment.newInstance(
                                parent.getAdapter().getItem(position).toString()))
                        .addToBackStack(null)
                        .commit();

            }
        });

        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();

        Cursor cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Albums._ID,
                        MediaStore.Audio.Albums.ARTIST,
                        MediaStore.Audio.Albums.ALBUM
                }, null, null, null);

        cursor.moveToFirst();

        if (mListView.getCount() == 0) {
            do {
                mAdapter.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
            } while (cursor.moveToNext());
        }
    }

}
