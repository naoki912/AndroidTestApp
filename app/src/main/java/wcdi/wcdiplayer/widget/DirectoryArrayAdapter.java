package wcdi.wcdiplayer.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

import wcdi.common.widget.GenericArrayAdapter;
import wcdi.wcdiplayer.R;

public class DirectoryArrayAdapter extends GenericArrayAdapter<File> {

    private final LayoutInflater inflater;

    public DirectoryArrayAdapter(Context context, int resource) {
        super(context, resource);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mLock = new Object();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        super.getView(position, convertView, parent);

        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.directory_list_item, parent, false);
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

}
