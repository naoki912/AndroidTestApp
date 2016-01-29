package wcdi.wcdiplayer;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MediaMetadataRetrieverArrayAdapter extends ArrayAdapter<MediaMetadataRetriever> {

    public MediaMetadataRetrieverArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);



        return view;
    }
}
