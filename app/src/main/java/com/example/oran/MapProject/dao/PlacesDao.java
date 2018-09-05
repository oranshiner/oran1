package com.example.oran.MapProject.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.oran.MapProject.beans.Place;

import static com.example.oran.MapProject.dao.PlacesDbConstants.PLACE_ID;
import static com.example.oran.MapProject.dao.PlacesDbConstants.TABLE_NAME;


//   ###################################################
//   ############    CREATED BY ORAN SHINER ############
//   ############         ON 08/2018        ############
//   ###################################################

public class PlacesDao {
    private String TAG = PlacesDao.class.getName();
    private PlacesDbHelper dbhelper;
    private final static String LOG_TAG = "PlacesDao";


    public PlacesDao(Context context) {
        dbhelper = new PlacesDbHelper(context, PlacesDbConstants.DATABASE_NAME, null,
                PlacesDbConstants.DATABASE_VERSION);
    }

    public long addPlace(Place place) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues newPlaceValues = new ContentValues();
        newPlaceValues.put(PlacesDbConstants.PLACE_NAME, place.getName());
        newPlaceValues.put(PlacesDbConstants.PLACE_ADDRESS, place.getAddress());
        newPlaceValues.put(PlacesDbConstants.PLACE_ALT, place.getAlt());
        newPlaceValues.put(PlacesDbConstants.PLACE_LNT, place.getLnt());
        newPlaceValues.put(PlacesDbConstants.PLACE_PICTURE, place.getPicture());

        long id;


        try {
            id = db.insertOrThrow(TABLE_NAME, null, newPlaceValues);
            return id;
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, "Failed to add a Place", ex);
            throw ex;
        } finally {
            db.close();
        }
    }

    public Cursor getAllPlaces() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null,
                null, null, null, null);
        return cursor;
    }

    public void deleteAllPlaces() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            db.delete(TABLE_NAME, null, null);
        } catch (SQLiteException ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
        } finally {
            db.close();
        }
    }

    public void deletePlace(long id) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            db.delete(TABLE_NAME, PLACE_ID + "=?",
                    new String[]{String.valueOf(id)});
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        } finally {
            db.close();
        }
    }

    public void updateMovie(Place place) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlacesDbConstants.PLACE_NAME, place.getName());
        values.put(PlacesDbConstants.PLACE_ADDRESS, place.getAddress());
        values.put(PlacesDbConstants.PLACE_ALT, place.getAlt());
        values.put(PlacesDbConstants.PLACE_LNT, place.getLnt());
        values.put(PlacesDbConstants.PLACE_PICTURE, place.getPicture());


        db.update(TABLE_NAME, values, PLACE_ID + "=" + place.getId(), null);
        db.close();
    }

    public boolean isPlaceExists(Place place) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                PlacesDbConstants.PLACE_NAME + "=?" + " AND " + PlacesDbConstants.PLACE_ADDRESS + "=?",
                new String[]{String.valueOf(place.getName()), String.valueOf(place.getAddress())}, null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public Place getPlace(long id) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Place place = null;
        Cursor cursor = db.query(TABLE_NAME, null, PLACE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        // Check if the Place was found
        if (cursor.moveToFirst()) {

            long placeId = cursor.getLong(cursor.getColumnIndex(PLACE_ID));
            String name = cursor.getString(cursor.getColumnIndex(PlacesDbConstants.PLACE_NAME));
            String address    = cursor.getString(cursor.getColumnIndex(PlacesDbConstants.PLACE_ADDRESS));

            int latitude   = cursor.getInt(cursor.getColumnIndex(PlacesDbConstants.PLACE_LNT));
            int longtitude = cursor.getInt(cursor.getColumnIndex(PlacesDbConstants.PLACE_ALT));

            String urlimage  = cursor.getString(cursor.getColumnIndex(PlacesDbConstants.PLACE_PICTURE));

            place = new Place(placeId, name, address , latitude, longtitude, urlimage);
        }
        return place;
    }
}

