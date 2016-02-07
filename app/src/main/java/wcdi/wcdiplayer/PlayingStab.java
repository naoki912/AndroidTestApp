package wcdi.wcdiplayer;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import wcdi.wcdiplayer.Items.SongObject;

public class PlayingStab extends Fragment {
    private static final String MEDIAPATHLIST = "mediapathlist";
    private static final String POSITION = "position";
    private static final String ALBUM_ART = "album_art";

    private static PlayingStab playingStab;
    private MediaPlayer mediaPlayer;
    
    public static PlayingStab newInstance(ArrayList<SongObject> mSongObjectList, int position) {

        if (playingStab == null) {

            playingStab = new PlayingStab();

            Bundle bundle = new Bundle();

            ArrayList<String> arrayList = new ArrayList<>();
            for (SongObject songObject : mSongObjectList) {
                arrayList.add(songObject.mPath);
            }
            bundle.putStringArrayList(MEDIAPATHLIST, arrayList);

            bundle.putInt(POSITION, position);

            bundle.putString(ALBUM_ART, mSongObjectList.get(position).mAlbumArt);

            playingStab.setArguments(bundle);
        }

        return playingStab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playing, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startMusic();
    }

    private PlayingStab(){}

    private void startMusic() {
        mediaPlayer = MediaPlayer.create(
                getActivity(),
                Uri.parse(getArguments().getStringArrayList(MEDIAPATHLIST).get(getArguments().getInt(POSITION)))
        );

        mediaPlayer.start();
    }

}
