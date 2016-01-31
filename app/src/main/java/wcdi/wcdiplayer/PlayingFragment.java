package wcdi.wcdiplayer;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PlayingFragment extends Fragment implements View.OnClickListener{
    //アルバムのファイルパス指定
    private String dir_path = "/storage/emulated/0/Download/FTL Original Soundtrack/";
    private File dir = new File(dir_path);
    private String[] file_list = dir.list();
    //何番目かを指定
    private int play_number = 3;
    private ArrayList<MediaPlayer> mediaplayer = new ArrayList<MediaPlayer>();
    private ImageButton pause_b,prev_b,next_b;

    public static PlayingFragment newInstance(String param1, String param2) {
        PlayingFragment fragment = new PlayingFragment();

        Bundle args = new Bundle();


        fragment.setArguments(args);

        return fragment;
    }

    public PlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int n = 0;n <= (file_list.length - 1);n++ ){
            mediaplayer.add(n,new MediaPlayer());
            try {
                mediaplayer.get(n).setDataSource(dir_path + file_list[n]);
                mediaplayer.get(n).prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (getArguments() != null) {
        }
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
        Log.d("Test","Check");
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
                mediaplayer.reset();
                if(play_number <= 0)play_number = file_list.length - 1;
                else{--play_number;}
                MusicStart();
                break;
            case R.id.next_button:
                mediaplayer.reset();
                if(play_number >= (file_list.length - 1))play_number = 0;
                else{++play_number;}
                MusicStart();
                break;
            default:
                break;
        }

    }

    public void MusicStart(){
        Log.d(String.valueOf(play_number),file_list[play_number]);
        mediaplayer.get(play_number).start();
    }
}
