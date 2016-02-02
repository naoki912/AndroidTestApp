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

import java.io.IOException;
import java.util.ArrayList;

public class PlayingFragment extends Fragment {

    private static String ARGUMENT1 = "point";

    private static String ARGUMENT2 = "media_list";

    private int position;

    private ArrayList<String> mediaPathList;

    private ImageButton pauseButton;

    private TextView titleView;

    private ImageView artworkView;

    private MediaPlayer mediaPlayer;

    public static PlayingFragment newInstance(ArrayList<String> mediaPathList, int point) {
        PlayingFragment fragment = new PlayingFragment();

        Bundle args = new Bundle();
        args.putInt(ARGUMENT1, point);
        args.putStringArrayList(ARGUMENT2, mediaPathList);

        fragment.setArguments(args);

        return fragment;
    }

    public PlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments();

            position = args.getInt(ARGUMENT1);

            mediaPathList = args.getStringArrayList(ARGUMENT2);
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
                position = (position > 0 ? position : mediaPathList.size()) - 1;

                startMusic();
            }
        });

        view.findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = (position + 1) % mediaPathList.size();

                startMusic();
            }
        });

        startMusic();
    }

    public void startMusic() {
        String path = mediaPathList.get(position);

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(path);

        MetaData metaData = MetaData.of(mediaMetadataRetriever);

        if (metaData.artwork != null) {
            artworkView.setImageBitmap(metaData.artwork);
        }

        if (metaData.title != null) {
            titleView.setText(metaData.title);
        }

        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                position = (position + 1) % mediaPathList.size();

                startMusic();
            }
        });
        mediaPlayer.start();

        Log.d("Debug: ", position + mediaPathList.get(position));
    }

    public static class MetaData {

        public final String title;

        public final String artist;

        public final Bitmap artwork;

        public MetaData(String title, String artist, Bitmap artwork) {
            this.title = title;
            this.artist = artist;
            this.artwork = artwork;
        }

        public static MetaData of(MediaMetadataRetriever mediaMetadataRetriever) {
            byte[] artwork = mediaMetadataRetriever.getEmbeddedPicture();

            return new MetaData(
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
                artwork == null ? null : BitmapFactory.decodeByteArray(artwork, 0, artwork.length)
            );

        }

    }

}
