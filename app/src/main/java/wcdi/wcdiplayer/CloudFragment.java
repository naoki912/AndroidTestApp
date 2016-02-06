package wcdi.wcdiplayer;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import wcdi.wcdiplayer.widget.AlbumViewAdapter;


public class CloudFragment extends Fragment {

    private static final String URL = "url";

    private String mUrl;

    private AlbumViewAdapter mAdapter;

    private AbsListView mListView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static CloudFragment newInstance(String url) {
        CloudFragment fragment = new CloudFragment();

        Bundle args = new Bundle();

        args.putString(URL, url);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         if (getArguments() != null) {
             mUrl = getArguments().getString(URL);
         }

        mAdapter = new AlbumViewAdapter(getActivity(), R.layout.cloud_list_item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cloud_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.add("a");
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_cloud_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();

                // http://dev.classmethod.jp/smartphone/swiperefreshlayout/
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        mAdapter.add("a");
    }

}
