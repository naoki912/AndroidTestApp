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

import wcdi.wcdiplayer.widget.DirectoryArrayAdapter;

public class DirectoryFragment extends Fragment {

    private static final String PATH = "path";

    public static DirectoryFragment newInstance(File file) {
        DirectoryFragment fragment = new DirectoryFragment();

        Bundle args = new Bundle();

        args.putString(PATH, file.toString());

        fragment.setArguments(args);

        return fragment;
    }

    private AbsListView mListView;

    private DirectoryArrayAdapter mAdapter;

    public DirectoryFragment() {
    }

    private OnFileClickListener mListener;

    private File path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }

        mAdapter = new DirectoryArrayAdapter(getActivity(), R.layout.directory_list_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                    mListener.onDirectoryClick(path);

                } else {

                    ArrayList<String> mediaPathList = new ArrayList<>();

                    for (File f : path.getParentFile().listFiles()) {
                        // ToDo if (音楽ファイルか判定) 音楽ファイルのみListにぶっこむ
                        // とりあえず音楽ファイルかの判定は後で考える

                        // not音楽ファイルがあった場合は追加しない & point--;
                        mediaPathList.add(f.getAbsolutePath());
                    }

                    // ここのpositionは、上で音楽ファイル以外があった場合、ずれる可能性があるので注意
                    mListener.onFileClick(mediaPathList, position);

                }

            }
        });

        path = new File(getArguments().getString(PATH));

        if (mListView.getCount() == 0) {
            try {
                mAdapter.addAll(path.listFiles());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage().toString());
            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnFileClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFileClickListener {
        void onDirectoryClick(File path);

        void onFileClick(ArrayList<String> mediaPathList, int position);
    }

}
