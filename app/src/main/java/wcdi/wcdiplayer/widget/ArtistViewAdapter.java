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

//        try {
//            File path = new File(getItem(position).mAlbumArt);
//            Bitmap bitmap = new BitmapFactory().decodeFile(path.getAbsolutePath());
//            ((ImageView) view.findViewById(R.id.album_image))
//                    .setImageBitmap(bitmap);
//        } catch (NullPointerException e) {
//             アルバムアートが定義されていない場合はぬるぽになる
//             新しく設定しなおしてあげないと、viewの再利用時に前回のジャケットが残ってしまう？
//             要検証
//            ((ImageView) view.findViewById(R.id.album_image))
//                    .setImageResource(R.drawable.default_album_art);
//        }

        return view;
    }
}
