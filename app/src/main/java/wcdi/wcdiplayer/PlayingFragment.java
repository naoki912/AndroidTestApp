package wcdi.wcdiplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import wcdi.wcdiplayer.Items.SongObject;

public class PlayingFragment extends Fragment {

    private static String ARGUMENT_1 = "point";

    private static String ARGUMENT_2 = "song_object_list";

    private static int sPosition;

    private static boolean sStarted = false;

    private  ArrayList<SongObject> mSongObjectList;

    private ImageButton mPauseButton;

    private TextView mTitleView, mArtistView, mAlbumView;

    private ImageView mArtworkView;

    private MediaPlayer mMediaPlayer;

    private static PlayingFragment sPlayingFragment;

    private OnPlayingFragmentListener mListener;

    public static PlayingFragment newInstance(ArrayList<SongObject> mSongObjectList, int point) {

        if (sPlayingFragment == null) {
            sPlayingFragment = new PlayingFragment();
        }else if(sPlayingFragment.mSongObjectList.get(sPosition).mPath.equals(mSongObjectList.get(point).mPath) && sPlayingFragment.mMediaPlayer.isPlaying()){
            sStarted = true;
            return sPlayingFragment;
        }else{
            sPlayingFragment.mMediaPlayer.stop();
            sPlayingFragment.mMediaPlayer.release();
        }
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_1, point);
        args.putSerializable(ARGUMENT_2, mSongObjectList);
        sPlayingFragment.setArguments(args);
        return sPlayingFragment;
    }

    public static PlayingFragment getInstance() {
        return sPlayingFragment;
    }

    private PlayingFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments();

            sPosition = args.getInt(ARGUMENT_1);

            mSongObjectList = (ArrayList<SongObject>)args.getSerializable(ARGUMENT_2);
        }

        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playing, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleView = (TextView) view.findViewById(R.id.title_view);

        mArtistView = (TextView) view.findViewById(R.id.artist_view);

        mAlbumView = (TextView) view.findViewById(R.id.album_view);

        mArtworkView = (ImageView) view.findViewById(R.id.artwark_view);

        mPauseButton = (ImageButton) view.findViewById(R.id.pause_button);

        mPauseButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mPauseButton.setImageResource(R.drawable.start);
                } else {
                    mMediaPlayer.start();
                    mPauseButton.setImageResource(R.drawable.pause);
                }
            }
        });

        view.findViewById(R.id.prev_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPosition = (sPosition > 0 ? sPosition : mSongObjectList.size()) - 1;
                startMusic();
            }
        });

        view.findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPosition = (sPosition + 1) % mSongObjectList.size();
                startMusic();
            }
        });

        startMusic();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnPlayingFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnPlayingFragmentListener");
        }

    }

    public void startMusic() {
        SongObject song = mSongObjectList.get(sPosition);

        if (song.mAlbumArt != null) {
            File path = new File(song.mAlbumArt);
            if (path.exists()) {
                Bitmap bitmap = new BitmapFactory().decodeFile(path.getAbsolutePath());
                mArtworkView.setImageBitmap(bitmap);
            } else {
                mArtworkView.setImageResource(R.drawable.default_album_art);
            }

        }


        if (song.mTitle != null) {
            mTitleView.setText(song.mTitle);
        }

        if(song.mArtist != null){
            mArtistView.setText((song.mArtist));
        }

        if(song.mAlbum != null){
            mAlbumView.setText(song.mAlbum);
        }
        mMediaPlayer.reset();

        if (!sStarted) {
            try {
                mMediaPlayer.setDataSource(song.mPath);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    sPosition = (sPosition + 1) % mSongObjectList.size();
                    startMusic();
                }
            });
            mMediaPlayer.start();

            mListener.onChangeSong(mSongObjectList.get(sPosition));

            Log.d("Debug: ", sPosition + mSongObjectList.get(sPosition).mTitle);
        }
        sStarted = false;
    }

    public String getPath(){
        return mSongObjectList.get(sPosition).mPath;
    }

    public interface OnPlayingFragmentListener {
        void onChangeSong(SongObject songObject);
    }
}
