package com.example.tour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tour.Model.TourDest;
import com.example.tour.Model.Tours;
import com.example.tour.Model.MyTours;
import com.example.tour.Model.Users;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tour.db";
    private static final String USER_TABLE = "user_tbl";
    public static final String TOUR_TABLE = "tour_tbl";
    public static final String TOUR_DEST_TABLE = "tour_dest_tbl";
    public static final String MY_TOUR_TABLE = "my_tour_tbl";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + USER_TABLE +
                "(userID TEXT PRIMARY KEY, userName TEXT, userEmail TEXT, userPass TEXT, userPhone TEXT)";
        db.execSQL(query);
        query = "CREATE TABLE " + TOUR_TABLE +
                "(tourID TEXT PRIMARY KEY, tourName TEXT, tourDate TEXT, tourPrice INTEGER, tourImage INTEGER)";
        db.execSQL(query);
        query = "CREATE TABLE " + TOUR_DEST_TABLE +
                "(tourDestID INTEGER PRIMARY KEY AUTOINCREMENT, tourDest TEXT, tourID TEXT, FOREIGN KEY (tourID) REFERENCES "+TOUR_TABLE+" (tourID))";
        db.execSQL(query);
        query = "CREATE TABLE " + MY_TOUR_TABLE +
                "(myTourId INTEGER PRIMARY KEY AUTOINCREMENT, tourID TEXT, userID TEXT, FOREIGN KEY (tourID) REFERENCES "+TOUR_TABLE+" (tourID), FOREIGN KEY (userID) REFERENCES "+USER_TABLE+" (userID))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + USER_TABLE;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TOUR_TABLE;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + MY_TOUR_TABLE;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean addUser(String userID, String userName, String userEmail, String userPass, String userPhone){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", userID);
        contentValues.put("userName", userName);
        contentValues.put("userEmail", userEmail);
        contentValues.put("userPass", userPass);
        contentValues.put("userPhone", userPhone);
        long result = db.insert(USER_TABLE,null,contentValues);
        db.close();
        return result == -1 ? false : true;
    }

    public boolean addtour(String tourID, String tourName, String tourDate, long tourPrice, int tourImages){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tourID", tourID);
        contentValues.put("tourName", tourName);
        contentValues.put("tourDate", tourDate);
        contentValues.put("tourPrice", tourPrice);
        contentValues.put("tourImage", tourImages);
        long result = db.insert(TOUR_TABLE, null, contentValues);
        db.close();
        return result == -1 ? false : true;
    }

    public boolean addTourDest(String tourDest, String tourID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tourDest", tourDest);
        contentValues.put("tourID", tourID);
        long result = db.insert(TOUR_DEST_TABLE, null, contentValues);
        db.close();
        return result == -1 ? false : true;
    }


    public boolean addMytour(String tourID, String userID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tourID", tourID);
        contentValues.put("userID", userID);
        long result = db.insert(MY_TOUR_TABLE, null, contentValues);
        db.close();
        return result == -1 ? false : true;
    }

    public Users getUser(String userID){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM "+ USER_TABLE + " WHERE userID = '"+ userID +"'", null);
        Users user = null;
        if(cursor.moveToNext())
            user = new Users(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        return user;
    }

    public ArrayList<Tours> viewtour(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TOUR_TABLE, null);
        ArrayList<Tours> toursList = new ArrayList<>();
        while (cursor.moveToNext()){
            toursList.add(new Tours(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getInt(4)));
        }
        return toursList;
    }

    public Tours getTourByID(String tourID){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TOUR_TABLE +" WHERE tourID = '"+tourID+"'", null);
        Tours tour = null;
        while (cursor.moveToNext()){
            tour = new Tours(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getInt(4));
        }
        return tour;
    }

    public ArrayList<TourDest> getTourDest(String tourID){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TOUR_DEST_TABLE+" WHERE tourID = '"+tourID+"'", null);
        ArrayList<TourDest> tourDestList = new ArrayList<>();
        while (cursor.moveToNext())
            tourDestList.add(new TourDest(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        return tourDestList;
    }

    public ArrayList<MyTours> viewMytour(String userID){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.myTourId, a.tourID, b.tourName, b.tourDate, b.tourImage, a.userID FROM "+ MY_TOUR_TABLE + " a JOIN "+ TOUR_TABLE +" b ON a.tourID = b.tourID WHERE a.userID = '"+ userID +"'", null);
        ArrayList<MyTours> myToursList = new ArrayList<>();
        while (cursor.moveToNext())
            myToursList.add(new MyTours(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5)));
        return myToursList;
    }

    public String loginUser(String userEmail, String userPass){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ USER_TABLE +" WHERE userEmail = '"+ userEmail +"' AND userPass = '"+ userPass +"'", null);
        Users user = null;
        if(cursor.moveToNext())
            user = new Users(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        return user != null ? user.getUserID() : "";
    }
}
