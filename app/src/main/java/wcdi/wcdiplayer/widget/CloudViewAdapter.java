package wcdi.wcdiplayer.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import wcdi.common.widget.GenericArrayAdapter;

import java.util.List;

public class CloudViewAdapter extends GenericArrayAdapter<String> {
    public CloudViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
