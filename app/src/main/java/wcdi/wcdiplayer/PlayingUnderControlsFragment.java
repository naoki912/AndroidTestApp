package wcdi.wcdiplayer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class PlayingUnderControlsFragment extends Fragment {

    private static PlayingUnderControlsFragment playingUnderControlsFragment;

    private TextView mTextView;

    private ImageView mImageView;

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

        mTextView = (TextView) view.findViewById(R.id.under_controls_title);

        mImageView = (ImageView) view.findViewById(R.id.under_controls_image);
    }

    public void setImage(String path) {
        // 渡す画像形式
        // mImageView.setimage
    }

    public void setTitle(String s) {
        mTextView.setText(s);
    }
}
