package wcdi.wcdiplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.ListFragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

import wcdi.wcdiplayer.Items.AlbumObject;
import wcdi.wcdiplayer.Items.SongObject;
import wcdi.wcdiplayer.widget.SongViewAdapter;

public class SongFragment extends ListFragment {

    private static final String ALBUM_ID = "album";

    private static final String ALBUM_ART = "album_art";

    private AbsListView mListView;

    private SongViewAdapter mAdapter;

    private OnSongClickListener mListener;

    private ArrayList<String> mSongPathList;

    public static SongFragment newInstance(AlbumObject albumObject) {
        SongFragment fragment = new SongFragment();

        Bundle args = new Bundle();

        args.putString(ALBUM_ID, String.valueOf(albumObject.mId));
        args.putString(ALBUM_ART, String.valueOf(albumObject.mAlbumArt));

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

        mSongPathList = new ArrayList<>();
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

                mListener.onSongClick(mSongPathList, position);

            }
        });

        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();

        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                SongObject.COLUMNS,
                MediaStore.Audio.Media.ALBUM_ID + "=?",
                new String[] {
                        getArguments().getString(ALBUM_ID)
                },
                null);

        cursor.moveToFirst();

        if (mListView.getCount() == 0) {
            do {
                mAdapter.add(new SongObject(cursor, getArguments().getString(ALBUM_ART)));

                mSongPathList.add(
                        ContentUris.withAppendedId(
                                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                        ).toString()
                );

            } while (cursor.moveToNext());
        }

        mAdapter.sort(new Comparator<SongObject>() {
            @Override
            public int compare(SongObject lhs, SongObject rhs) {
                return lhs.mTrack - rhs.mTrack;
            }
        });

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
        void onSongClick(ArrayList<String> mediaPathList, int position);
    }

}
