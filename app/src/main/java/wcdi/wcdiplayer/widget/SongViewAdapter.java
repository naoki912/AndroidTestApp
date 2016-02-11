package wcdi.wcdiplayer.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import wcdi.common.widget.GenericArrayAdapter;
import wcdi.wcdiplayer.Items.SongObject;
import wcdi.wcdiplayer.R;

public class SongViewAdapter extends GenericArrayAdapter<SongObject> {

    public SongViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        ((TextView) view.findViewById(R.id.song_title)).setText(getItem(position).mTitle);

        // 汚いのでもっとマシな方法を探す
        Long aLong = Long.valueOf(getItem(position).mTrack);
        String string = String.valueOf(aLong.longValue() % 100);
        ((TextView) view.findViewById(R.id.song_number)).setText(string);

        // もう少しきれいに書ける気がする
        Integer time = (int) getItem(position).mDuration / 1000;
        Integer minute = time / 60;
        Integer second = time % 60;
        if (second < 10) {
            ((TextView) view.findViewById(R.id.song_time))
                    .setText(minute.toString() + ":0" + second.toString());
        } else {
            ((TextView) view.findViewById(R.id.song_time))
                    .setText(minute.toString() + ":" + second.toString());
        }

        if (getItem(position).mAlbumArt != null) {
            File path = new File(getItem(position).mAlbumArt);
            Bitmap bitmap = new BitmapFactory().decodeFile(path.getAbsolutePath());
            ((ImageView) view.findViewById(R.id.song_image))
                    .setImageBitmap(bitmap);
        } else {
            ((ImageView) view.findViewById(R.id.song_image))
                    .setImageResource(R.drawable.default_album_art);
        }

        return view;
    }

}
