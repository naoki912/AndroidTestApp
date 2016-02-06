package wcdi.wcdiplayer;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PlayingStab extends Fragment {
    private static final String MEDIAPATHLIST = "mediapathlist";
    private static final String POSITION = "position";
    private static PlayingStab playingStab;
    private MediaPlayer mediaPlayer;
    
    public static PlayingStab newInstance(ArrayList<String> mediaPathList, int position) {
        if (playingStab == null) {
            playingStab = new PlayingStab();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(MEDIAPATHLIST, mediaPathList);
            bundle.putInt(POSITION, position);
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
