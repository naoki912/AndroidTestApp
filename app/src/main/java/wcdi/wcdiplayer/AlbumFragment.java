package wcdi.wcdiplayer;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.File;

import wcdi.wcdiplayer.widget.AlbumArrayAdapter;

public class AlbumFragment extends Fragment implements AbsListView.OnItemClickListener {

    public static AlbumFragment newInstance(File file) {
        AlbumFragment fragment = new AlbumFragment();

        Bundle args = new Bundle();

        args.putString("path", file.toString());

        fragment.setArguments(args);

        return fragment;
    }

    private AbsListView mListView;

    private AlbumArrayAdapter mAdapter;

    public AlbumFragment() {
    }

    private File path;

    @Override
    public void setArguments(Bundle args) {
        path = new File(args.getString("path"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }

        mAdapter = new AlbumArrayAdapter(getActivity(), R.layout.album_list_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        if (mListView.getCount() == 0) {
            try {
                mAdapter.addAll(path.listFiles());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage().toString());
            }
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // ToDo 現在はadapterのTextViewに表示されているファイル名からpathを生成しているので、
        // itemにpathを保存しておく変数か何かを追加する
        TextView directoryName = (TextView) view.findViewById(R.id.directoryName);
        path = new File(path.toString() + "/" + directoryName.getText().toString());

        if (path.isDirectory()) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, AlbumFragment.newInstance(path))
                    .addToBackStack(null)
                    .commit();
        } else {
            // ここでListを作成する処理を実装し、Activity経由でPlayingServiceへListを渡す
            // コールバックインターフェイスを定義して、イベントをActivity側に飛ばして
            // Activity側でPlayingFragmentを生成する

//            List<String> stringList = null;
            // とりあえず音楽ファイルかの判定は後で考える
//            for (File f : path.getParentFile().listFiles()) {
//                stringList.add(f.toString());
//            }
            getFragmentManager()
                    .beginTransaction()
//                    .replace(R.id.fragment, PlayingFragment.newInstance(stringList, null))
                    .replace(R.id.fragment, PlayingFragment.newInstance(path.getParentFile(), null))
                    .addToBackStack(null)
                    .commit();
        }

    }

}
