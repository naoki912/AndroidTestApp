package wcdi.wcdiplayer.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wcdi.common.widget.GenericArrayAdapter;
import wcdi.wcdiplayer.Items.ArtistObject;
import wcdi.wcdiplayer.R;

public class ArtistViewAdapter extends GenericArrayAdapter<ArtistObject> {

    public ArtistViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        ((TextView) view.findViewById(R.id.album_name))
                .setText(getItem(position).mArtist);

        ((TextView) view.findViewById(R.id.album_artist))
                .setText("");

        return view;
    }
}
