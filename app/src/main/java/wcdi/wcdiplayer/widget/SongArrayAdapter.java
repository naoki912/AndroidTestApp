package wcdi.wcdiplayer.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import wcdi.common.widget.GenericArrayAdapter;
import wcdi.wcdiplayer.R;

public class SongArrayAdapter extends GenericArrayAdapter<String> {

    public SongArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        ((TextView) view.findViewById(R.id.title)).setText(getItem(position).toString());

        ((TextView) view.findViewById(R.id.number)).setText(String.valueOf(position));

        return view;
    }

}
