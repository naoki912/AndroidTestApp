package wcdi.wcdiplayer.Items;

import android.database.Cursor;
import android.provider.MediaStore;

public class ArtistObject {

    public static final String[] COLUMNS = {
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
    };

    public long mId;
    public String mArtist;

    public ArtistObject(Cursor cursor) {
        mId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
        mArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
    }
}

