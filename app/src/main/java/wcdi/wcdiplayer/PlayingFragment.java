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

public class PlayingFragment extends Fragment implements View.OnClickListener{
    //アルバムのファイルパス指定
//    private String dir_path = "/storage/emulated/0/Download/FTL Original Soundtrack/";
    private String dir_path;
//    private File dir = new File(dir_path);
    private File dir;
//    private String[] file_list = dir.list();
    private String[] file_list;
//    private List<String> file_list;
//    static private int play_number = 3;
    static private int play_number = 0;
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
        mediaplayer = new MediaPlayer();
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
        Log.d("Test","Test");
        switch (view.getId()) {
            case R.id.pause_button:
                if (mediaplayer.isPlaying()) {
                    Log.d("Test","pause");
                    mediaplayer.pause();
                } else {
                    Log.d("Test","start");
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
        try {
            mediaplayer.setDataSource(dir_path + file_list[play_number]);
            mediaplayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(String.valueOf(play_number),file_list[play_number]);
        mediaplayer.start();
    }

    public void onBeforeClick(){

    }
}
