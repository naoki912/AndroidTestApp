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
import wcdi.wcdiplayer.Items.AlbumObject;
import wcdi.wcdiplayer.R;


public class AlbumViewAdapter extends GenericArrayAdapter<AlbumObject> {
    public AlbumViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        ((TextView) view.findViewById(R.id.album_name))
                .setText(getItem(position).mAlbum);

        ((TextView) view.findViewById(R.id.album_artist))
                .setText(getItem(position).mArtist);

        try {
            File path = new File(getItem(position).mAlbumArt);
            Bitmap bitmap = new BitmapFactory().decodeFile(path.getAbsolutePath());
            ((ImageView) view.findViewById(R.id.album_image))
                    .setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            // アルバムアートが定義されていない場合はぬるぽになる
            // 新しく設定しなおしてあげないと、viewの再利用時に前回のジャケットが残ってしまう？
            // 要検証
            ((ImageView) view.findViewById(R.id.album_image))
                    .setImageResource(R.drawable.default_album_art);
        }

        return view;
    }
}
