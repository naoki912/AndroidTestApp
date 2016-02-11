package wcdi.wcdiplayer.Items;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.Serializable;


public class SongObject implements Serializable {

    public static final String[] COLUMNS = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.TRACK
    };

    public long mId;
    public String mPath;
    public String mTitle;
    public String mAlbum;
    public String mArtist;
    public Uri uri;
    public long mDuration; //再生時間
    public int mTrack;

    public String mAlbumArt;

    public  SongObject(Cursor cursor) {
        this(cursor, null);
    }

    public  SongObject(Cursor cursor, String albumArt) {
        mId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        mPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        mTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        mAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
        mArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mId);
        mDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)); //再生時間
        mTrack = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
        mAlbumArt = albumArt;
    }
}
