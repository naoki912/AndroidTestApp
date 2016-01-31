package wcdi.wcdiplayer.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.Collection;
import java.util.List;

import wcdi.common.widget.GenericArrayAdapter;
import wcdi.wcdiplayer.R;

public class AlbumArrayAdapter extends GenericArrayAdapter<File> {

    private final LayoutInflater inflater;

    private List<File> mObjects;

    private final Object mLock;

    public AlbumArrayAdapter(Context context, int resource) {
        super(context, resource);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mLock = new Object();
    }

    @Override
    public File getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.album_list_item, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(R.id.directoryName);
        try {
            textView.setText(getItem(position).getName());
        } catch (Exception e) {
            textView.setText("ファイル名が壊れています");
        }

        return view;
    }

    @Override
    public void addAll(Collection<? extends File> objects) {
        synchronized (mLock) {
            mObjects.addAll(objects);
        }

        notifyDataSetChanged();
    }

    @Override
    public void remove(File object) {
        synchronized (mLock) {
            mObjects.remove(object);
        }

        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        synchronized (mLock) {
            mObjects.clear();
        }

        notifyDataSetChanged();
    }
}
