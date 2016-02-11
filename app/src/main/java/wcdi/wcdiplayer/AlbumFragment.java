package wcdi.wcdiplayer;

import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.util.ArrayList;

import wcdi.wcdiplayer.Items.AlbumObject;
import wcdi.wcdiplayer.Items.SongObject;
import wcdi.wcdiplayer.widget.AlbumViewAdapter;


public class AlbumFragment extends Fragment {

    private AlbumViewAdapter mAdapter;

    public static AlbumFragment newInstance() {

        return new AlbumFragment();

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

        return inflater.inflate(R.layout.fragment_album_list, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        AbsListView mListView = (AbsListView) view.findViewById(android.R.id.list);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();

                Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        SongObject.COLUMNS,
                        MediaStore.Audio.Media.ALBUM_ID + "=?",
                        new String[]{
                                String.valueOf(((AlbumObject) parent.getAdapter().getItem(position)).mId)},
                        null);

                cursor.moveToFirst();

                ArrayList<SongObject> songObjects = new ArrayList<>();

                // do/whileとかダサい
                do {

                    songObjects.add(new SongObject(cursor, mAdapter.getItem(position).mAlbumArt));

                } while (cursor.moveToNext());

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, SongFragment.newInstance(songObjects))
                        .addToBackStack(null)
                        .commit();

            }

        });

        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();

        Cursor cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                AlbumObject.COLUMNS, null, null, null);

        cursor.moveToFirst();

        if (mListView.getCount() == 0) {

            // do/whileとかダサい
            do {

                mAdapter.add(new AlbumObject(cursor));

            } while (cursor.moveToNext());

        }

    }

}
