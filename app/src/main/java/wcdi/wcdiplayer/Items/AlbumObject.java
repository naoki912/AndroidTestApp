package wcdi.wcdiplayer.Items;

import android.database.Cursor;
import android.provider.MediaStore;

public class AlbumObject {

    public static final String[] COLUMNS = {
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.ALBUM_ART
    };

    public long mId;
    public String mAlbum;
    public String mArtist;
    public String mAlbumArt;

    public AlbumObject(Cursor cursor) {
        mId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
        mAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
        mArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
        mAlbumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
    }
}
