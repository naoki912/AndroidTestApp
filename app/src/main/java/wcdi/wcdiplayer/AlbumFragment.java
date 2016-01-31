package wcdi.wcdiplayer;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.io.File;
import java.util.ArrayList;

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

    private OnAlbumFileClickListener mListner;

    private File path;

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

        Bundle args = getArguments();
        path = new File(args.getString("path"));

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

        try {
            mListner = (OnAlbumFileClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // 現在はadapterのTextViewに表示されているファイル名からpathを生成しているので、
        // itemにpathを保存しておく変数か何かを追加する
        // TextView directoryName = (TextView) view.findViewById(R.id.directoryName);
        // path = new File(path.toString() + "/" + directoryName.getText().toString());
        // ↓
        // 変更済みだが、この方法で正しいのか分からない

        path = new File(parent.getAdapter().getItem(position).toString());

        if (path.isDirectory()) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, AlbumFragment.newInstance(path))
                    .addToBackStack(null)
                    .commit();
        } else {

            // PlayingFragment側のBundleには、再生ポイント(int)、List(Bundleの仕様上ArrayList)を渡す
            // Bundleの仕様上ArrayListを使用
            ArrayList<String> mediaPathList = new ArrayList<>();
            for (File f: path.getParentFile().listFiles()) {
                // ToDo if (音楽ファイルか判定)
                // 音楽ファイルのみListにぶっこむ
                // とりあえず音楽ファイルかの判定は後で考える
                mediaPathList.add(f.getAbsolutePath());
            }

            mListner.onAlbumFileClick(mediaPathList, 0);

        }

    }

    public interface OnAlbumFileClickListener {
        void onAlbumFileClick(ArrayList<String> mediaPathList, int point);
    }

}
