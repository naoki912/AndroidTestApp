package wcdi.wcdiplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
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
import java.io.Serializable;

import wcdi.wcdiplayer.Items.SongObject;

public class PlayingFragment extends Fragment {

    private static String ARGUMENT1 = "point";

    private static String ARGUMENT2 = "song_object_list";

    private static int position;

    private static boolean started = false;

    private  ArrayList<SongObject> songObjectList;

    private ImageButton pauseButton;

    private TextView titleView;

    private ImageView artworkView;

    private MediaPlayer mediaPlayer;

    private static PlayingFragment mPlayingFragment;

    public static PlayingFragment newInstance(ArrayList<SongObject> mSongObjectList, int point) {

        if (mPlayingFragment == null) {
            mPlayingFragment = new PlayingFragment();
        }else if(mPlayingFragment.songObjectList.get(position).mPath.equals(mSongObjectList.get(point).mPath) && mPlayingFragment.mediaPlayer.isPlaying()){
            started = true;
            return mPlayingFragment;
        }else{
            mPlayingFragment.mediaPlayer.stop();
            mPlayingFragment.mediaPlayer.release();
        }
        Bundle args = new Bundle();
        args.putInt(ARGUMENT1, point);
        args.putSerializable(ARGUMENT2, mSongObjectList);
        mPlayingFragment.setArguments(args);
        return mPlayingFragment;
    }

    public static PlayingFragment getInstance() {
        return mPlayingFragment;
    }

    private PlayingFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments();

            position = args.getInt(ARGUMENT1);

            songObjectList = (ArrayList<SongObject>)args.getSerializable(ARGUMENT2);
        }

        mediaPlayer = new MediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playing, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleView = (TextView) view.findViewById(R.id.titleView);

        artworkView = (ImageView) view.findViewById(R.id.artwark_view);

        pauseButton = (ImageButton) view.findViewById(R.id.pause_button);

        pauseButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        });

        view.findViewById(R.id.prev_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = (position > 0 ? position : songObjectList.size()) - 1;

                startMusic();
            }
        });

        view.findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = (position + 1) % songObjectList.size();

                startMusic();
            }
        });

        startMusic();
    }

    public void startMusic() {
        SongObject song = songObjectList.get(position);

        if (song.mAlbumArt != null) {
            File path = new File(song.mAlbumArt);
            if (path.exists()) {
                Bitmap bitmap = new BitmapFactory().decodeFile(path.getAbsolutePath());
                ((ImageView) artworkView.findViewById(R.id.artwark_view))
                        .setImageBitmap(bitmap);
            } else {
                ((ImageView) artworkView.findViewById(R.id.artwark_view))
                        .setImageResource(R.drawable.default_album_art);
            }

        }

        if (song.mTitle != null) {
            titleView.setText(song.mTitle);
        }

        mediaPlayer.reset();

        Log.d("path", song.mPath);

        if (!started) {
            try {
                mediaPlayer.setDataSource(song.mPath);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    position = (position + 1) % songObjectList.size();

                    startMusic();
                }
            });
            mediaPlayer.start();

            Log.d("Debug: ", position + songObjectList.get(position).mTitle);
        }
        started = false;
    }

    public String getPath(){
        return songObjectList.get(position).mPath;
    }
}
