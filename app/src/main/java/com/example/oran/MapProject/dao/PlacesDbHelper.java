package com.example.oran.MapProject.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class PlacesDbHelper extends SQLiteOpenHelper {

    private static String TAG = "PlacesDBHelper";

    public PlacesDbHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating all the tables");
        String CREATE_PLACES_TABLE = "CREATE TABLE " + PlacesDbConstants.TABLE_NAME +
                "(" + PlacesDbConstants.PLACE_ID+ " INTEGER PRIMARY KEY," +
                PlacesDbConstants.PLACE_NAME + " TEXT," +
                PlacesDbConstants.PLACE_ADDRESS + " TEXT," +
                PlacesDbConstants.PLACE_ALT +  " REAL," +
                PlacesDbConstants.PLACE_LNT +  " REAL," +
                PlacesDbConstants.PLACE_PICTURE +  " TEXT" +
                ")";
        try {
            db.execSQL(CREATE_PLACES_TABLE);
        } catch (SQLiteException ex) {
            Log.e(TAG, "Create table exception: " +
                    ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old date");
        db.execSQL("DROP TABLE IF EXISTS " + PlacesDbConstants.TABLE_NAME);
        onCreate(db);
    }
}
