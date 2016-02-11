package wcdi.wcdiplayer;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import wcdi.wcdiplayer.Items.SongObject;


public class PlayingUnderControlsFragment extends Fragment {

    private static PlayingUnderControlsFragment playingUnderControlsFragment;

    private TextView mTitleView;

    private TextView mArtistView;

    private ImageView mAlbumArtView;

    public static PlayingUnderControlsFragment getInstance() {

        if (playingUnderControlsFragment == null) {

            playingUnderControlsFragment = new PlayingUnderControlsFragment();

        }

        return playingUnderControlsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playing_under_controls, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mTitleView = (TextView) view.findViewById(R.id.under_controls_title);

        mArtistView = (TextView) view.findViewById(R.id.under_controls_artist);

        mAlbumArtView = (ImageView) view.findViewById(R.id.under_controls_image);

    }

    public void setSong(SongObject songObject) {

        mTitleView.setText(songObject.mTitle);

        mArtistView.setText(songObject.mAlbum);

        if (songObject.mAlbumArt != null) {
            File path = new File(songObject.mAlbumArt);
            mAlbumArtView.setImageBitmap(new BitmapFactory().decodeFile(path.getAbsolutePath()));
        } else {
            mAlbumArtView.setImageResource(R.drawable.default_album_art);
        }

    }
}
