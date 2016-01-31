package wcdi.wcdiplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayingFragment extends Fragment implements View.OnClickListener{
    private String dir_path;
    private File dir;
    private String[] file_list;
//    private List<String> file_list;;
//何番目かを指定
    private int play_number = 0;
    private MediaPlayer mediaplayer;
    private ImageButton pause_b,prev_b,next_b;

    public static PlayingFragment newInstance(File param1, String param2) {
        PlayingFragment fragment = new PlayingFragment();

        Bundle args = new Bundle();

        args.putString("PATH", param1.toString());

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        // dir_pathの最後に"/"が無いと、"dir_path + file_list[play_number]"
        // した場合にディレクトリとファイル名の間に"/"が無くてくっついちゃうので
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
        MusicStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playing, container, false);
        pause_b = (ImageButton)view.findViewById(R.id.pause_button);
        prev_b = (ImageButton)view.findViewById(R.id.prev_button);
        next_b = (ImageButton)view.findViewById(R.id.next_button);

        pause_b.setOnClickListener(this);
        prev_b.setOnClickListener(this);
        next_b.setOnClickListener(this);

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
        Log.d("Test", "Check");
    }

    public void MusicStart(){
        try {
            mediaplayer.setDataSource(dir_path + file_list[play_number]);
            mediaplayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaplayer.start();
        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("Test", "Check");
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
    }
}
