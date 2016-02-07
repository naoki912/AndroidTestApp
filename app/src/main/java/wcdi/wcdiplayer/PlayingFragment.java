package wcdi.wcdiplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
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

    private static String ARGUMENT1 = "point";

    private static String ARGUMENT2 = "song_object_list";

    private static int position, textPosition;

    private static boolean started = false;

    private  ArrayList<SongObject> songObjectList;

    private ImageButton pauseButton;

    private TextView titleView;

    private ImageView artworkView;

    private MediaPlayer mediaPlayer;

    private static Handler handler = new Handler();

    private Runnable updataText;

    private static PlayingFragment playingFragment;

    private OnPlayingFragmentListener mListener;

    public static PlayingFragment newInstance(ArrayList<SongObject> mSongObjectList, int point) {

        if (playingFragment == null) {
            playingFragment = new PlayingFragment();
        }else if(playingFragment.songObjectList.get(position).mPath.equals(mSongObjectList.get(point).mPath) && playingFragment.mediaPlayer.isPlaying()){
            started = true;
            return playingFragment;
        }else{
            playingFragment.mediaPlayer.stop();
            playingFragment.mediaPlayer.release();
        }
        Bundle args = new Bundle();
        args.putInt(ARGUMENT1, point);
        args.putSerializable(ARGUMENT2, mSongObjectList);
        playingFragment.setArguments(args);
        return playingFragment;
    }

    public static PlayingFragment getInstance() {
        return playingFragment;
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
                    pauseButton.setImageResource(R.drawable.start);
                } else {
                    mediaPlayer.start();
                    pauseButton.setImageResource(R.drawable.pause);
                }
            }
        });

        view.findViewById(R.id.prev_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = (position > 0 ? position : songObjectList.size()) - 1;
                mListener.onChangeSong(songObjectList.get(position));
                startMusic();
            }
        });

        view.findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = (position + 1) % songObjectList.size();
                mListener.onChangeSong(songObjectList.get(position));
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
            throw new ClassCastException(activity.toString() + " must implement OnSongClickListener");
        }

    }

    public void startMusic() {
        SongObject song = songObjectList.get(position);

        if (song.mAlbumArt != null) {
            File path = new File(song.mAlbumArt);
            if (path.exists()) {
                Bitmap bitmap = new BitmapFactory().decodeFile(path.getAbsolutePath());
                artworkView.setImageBitmap(bitmap);
            } else {
                artworkView.setImageResource(R.drawable.default_album_art);
            }

        }


        if (song.mTitle != null) {
            titleView.setText(song.mTitle);
        }
        textPosition= 1;

        updataText = new Runnable() {
            @Override
            public void run() {
                SongObject song = songObjectList.get(position);
                if (textPosition == 0) {
                    if (song.mTitle != null) {
                        titleView.setText(song.mTitle);
                    }else{
                        titleView.setText("名称不明");
                    }
                    textPosition = 1;
                }else if(textPosition == 1){
                    if(song.mArtist != null){
                        titleView.setText((song.mArtist));
                    }else{
                        titleView.setText("アーティスト不明");
                    }
                    textPosition = 2;
                }else if(textPosition == 2){
                    if(song.mAlbum != null){
                        titleView.setText(song.mAlbum);
                    }else{
                        titleView.setText("アルバム不明");
                    }
                    textPosition = 0;
                }
                handler.removeCallbacks(updataText);
                handler.postDelayed(updataText,3000);
            }
        };

        handler.postDelayed(updataText,3000);

        mediaPlayer.reset();

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
                    mListener.onChangeSong(songObjectList.get(position));
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

    public interface OnPlayingFragmentListener {
        void onChangeSong(SongObject songObject);
    }
}
