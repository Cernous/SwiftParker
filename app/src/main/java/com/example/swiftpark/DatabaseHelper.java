package com.example.swiftpark;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context = null;

    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(@Nullable Context context) {
        super(context, Config.DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creates Spots Table
        String CREATE_SPOT_TABLE = "CREATE TABLE " + Config.TABLE_SPOT + " ("
                + Config.COLUMN_SPOT_ID + " INTEGER PRIMARY KEY, "
                + Config.COLUMN_SPOT_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_SPOT_AVAILABLE + " TEXT NOT NULL, "
                + Config.COLUMN_SPOT_CHECK + " INTEGER NOT NULL)";

        sqLiteDatabase.execSQL(CREATE_SPOT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_SPOT);
        onCreate(sqLiteDatabase);
    }


    public int insertSpot(Spot spot){
        int id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_SPOT_ID, spot.getId());
        contentValues.put(Config.COLUMN_SPOT_NAME, spot.getName());
        contentValues.put(Config.COLUMN_SPOT_AVAILABLE, spot.getAvailable());
        contentValues.put(Config.COLUMN_SPOT_CHECK, spot.getCheck());


        try{
            id = (int) db.insertOrThrow(Config.TABLE_SPOT, null, contentValues);
          //  updateAccess(profile.getId(), "Created"); // if a profile is inserted then Created will also be added to access table
        }catch (SQLiteException e){
            Toast.makeText(context, "insertSpot Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            db.close();
        }
        return id;
    }



    // Retrievs all spots
    public List<Spot> getAllProfiles(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        List<Spot> spotList = new ArrayList<>();

        try{
            cursor = db.query(Config.TABLE_SPOT, null, null, null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SPOT_ID));
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SPOT_NAME));
                        @SuppressLint("Range") String available = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SPOT_AVAILABLE));
                        @SuppressLint("Range") int check = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SPOT_CHECK));
                        Spot spot = new Spot(id, name, available, check);
                        spotList.add(spot);
                    }while (cursor.moveToNext());
                    return spotList;
                }
            }
        }catch (SQLiteException e){
            Toast.makeText(context, "getAllSpots Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            db.close();
        }
        return Collections.emptyList();
    }


    /*
    // returns all access records for the access listview in the profileActivity
    public List<String> getAccessRecordsForProfile(int profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> accessRecords = new ArrayList<>();

        // Sorts list by time
        String query = "SELECT " + Config.COLUMN_ACCESS_TYPE + ", " + Config.COLUMN_ACCESS_TIME + " FROM " + Config.TABLE_ACCESS +
                " WHERE " + Config.COLUMN_ACCESS_PROFILE_ID + " = " + profileId +
                " ORDER BY " + Config.COLUMN_ACCESS_TIME + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String accessType = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ACCESS_TYPE));
                @SuppressLint("Range") String accessTime = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ACCESS_TIME));
                accessRecords.add(accessType + ": " + accessTime);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return accessRecords;
    }

    */

    // deletes profile
    public void deleteProfile(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        String spotTable = Config.TABLE_SPOT;


        int rowsDeleted = db.delete(spotTable, whereClause, whereArgs);

        db.close();

        if (rowsDeleted > 0) {
            Toast.makeText(context, "Row Deleted", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, "Operation Failed", Toast.LENGTH_LONG).show();
        }
    }

/*
    // whenever this function is called it will add a new entry for the id and desired type depending on where it was called
    public void updateAccess(int profileId, String accessType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Config.COLUMN_ACCESS_PROFILE_ID, profileId);
        values.put(Config.COLUMN_ACCESS_TYPE, accessType);

        db.insert(Config.TABLE_ACCESS, null, values);
        db.close();

    }
*/
}

