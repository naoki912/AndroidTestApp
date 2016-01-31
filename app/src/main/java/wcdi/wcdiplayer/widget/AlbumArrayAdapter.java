package wcdi.wcdiplayer.widget;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
//            textView.setText(getItem(position));
            textView.setText(getItem(position).getPath());
        } catch (Exception e) {
            textView.setText("except");
        }

        return view;
//        return super.getView(position, convertView, parent);

    }

    @Override
    public void addAll(Collection<? extends File> objects) {
        synchronized (mLock) {
            mObjects.addAll(objects);
        }

        notifyDataSetChanged();

//        super.addAll(objects);
    }

    @Override
    public void remove(File object) {
        synchronized (mLock) {
            mObjects.remove(object);
        }

        notifyDataSetChanged();

//        super.remove(object);
    }

    @Override
    public void clear() {
        synchronized (mLock) {
            mObjects.clear();
        }

        notifyDataSetChanged();

//        super.clear();
    }
}
