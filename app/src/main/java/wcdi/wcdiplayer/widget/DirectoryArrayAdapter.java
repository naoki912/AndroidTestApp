package wcdi.wcdiplayer.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

import wcdi.common.widget.GenericArrayAdapter;
import wcdi.wcdiplayer.R;

public class DirectoryArrayAdapter extends GenericArrayAdapter<File> {

    public DirectoryArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        TextView textView = (TextView) view.findViewById(R.id.directoryName);

        try {
            textView.setText(getItem(position).getName());
        } catch (Exception e) {
            textView.setText("ファイル名が壊れています");
        }

        return view;
    }

}
