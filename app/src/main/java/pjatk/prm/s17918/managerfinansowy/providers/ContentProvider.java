package pjatk.prm.s17918.managerfinansowy.providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import pjatk.prm.s17918.managerfinansowy.databases.DatabaseHelper;

public class ContentProvider extends android.content.ContentProvider {
    private DatabaseHelper helper;
    public ContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return helper.getReadableDatabase().query("EVENTS_TABLE", projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
