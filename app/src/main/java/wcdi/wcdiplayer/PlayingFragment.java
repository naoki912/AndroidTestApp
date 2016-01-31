package wcdi.wcdiplayer;

import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayingFragment extends Fragment implements View.OnClickListener{
    private int play_number = 0;
    private String dir_path,full_path,title;
    private byte[] artwork;
    private String[] file_list;
    private File dir;
    private TextView title_v;
    private ImageView artwork_v;
    private ImageButton pause_b,prev_b,next_b;
    private MediaPlayer mediaplayer;
    private MediaMetadataRetriever mediametadataretriever;

    public static PlayingFragment newInstance(File param1, String param2) {
        PlayingFragment fragment = new PlayingFragment();

        Bundle args = new Bundle();

        args.putString("PATH", param1.toString());

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        dir_path = args.getString("PATH") + "/";

        dir = new File(dir_path);

        file_list = dir.list();
    }

    public PlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
        mediaplayer = new MediaPlayer();
        mediametadataretriever = new MediaMetadataRetriever();
        PathSet();
        MusicStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playing, container, false);
        pause_b = (ImageButton)view.findViewById(R.id.pause_button);
        prev_b = (ImageButton)view.findViewById(R.id.prev_button);
        next_b = (ImageButton)view.findViewById(R.id.next_button);
        title_v = (TextView)view.findViewById(R.id.titleView);
        artwork_v = (ImageView)view.findViewById(R.id.artwark_view);

        pause_b.setOnClickListener(this);
        prev_b.setOnClickListener(this);
        next_b.setOnClickListener(this);

        TextSet();
        ArtworkSet();

        return view;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.pause_button:
                if (mediaplayer.isPlaying()) {
                    Log.d(String.valueOf(play_number) + " : " + file_list[play_number],"pause");
                    mediaplayer.pause();
                } else {
                    Log.d(String.valueOf(play_number) + " : " + file_list[play_number],"start");
                    mediaplayer.start();
                }
                break;
            case R.id.prev_button:
                MusicSet(false);
                MusicStart();

                break;
            case R.id.next_button:
                MusicSet(true);
                MusicStart();

                break;
            default:
                break;
        }
    }

    public void MusicStart(){
        try {
            mediaplayer.setDataSource(full_path);
            mediaplayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaplayer.start();
        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                MusicSet(true);
                MusicStart();
            }
        });
        Log.d(String.valueOf(play_number), file_list[play_number]);
    }

    public void MusicSet(boolean arrow){
        mediaplayer.reset();
        if(arrow){
            if(play_number >= (file_list.length - 1))play_number = 0;
            else{++play_number;}
        }else{
            if(play_number <= 0)play_number = file_list.length - 1;
            else{--play_number;}
        }
        PathSet();
        TextSet();
        ArtworkSet();
    }

    public void ArtworkSet(){
        artwork = mediametadataretriever.getEmbeddedPicture();
        if(artwork == null){
            Log.d("picture","null");
        }else {
            artwork_v.setImageBitmap(BitmapFactory.decodeByteArray(artwork, 0, artwork.length));
        }
    }

    public void TextSet(){
        title = mediametadataretriever.extractMetadata(mediametadataretriever.METADATA_KEY_TITLE);
        if(title == null){
            Log.d("title","null");
        }else{
            title_v.setText(title);
        }
    }

    public void PathSet(){
        full_path = dir_path + file_list[play_number];
        mediametadataretriever.setDataSource(full_path);
    }
}
