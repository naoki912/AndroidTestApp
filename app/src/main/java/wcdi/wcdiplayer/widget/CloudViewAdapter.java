package wcdi.wcdiplayer.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wcdi.common.widget.GenericArrayAdapter;
import wcdi.wcdiplayer.R;

import java.util.List;

public class CloudViewAdapter extends GenericArrayAdapter<String> {
    public CloudViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        TextView textView = (TextView) view.findViewById(R.id.cloudFileName);
        textView.setText(getItem(position));

        return view;
    }
}
