package com.example.prabhav.assignment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhav on 15-04-2015.
 */


import android.annotation.TargetApi;
import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.util.Log;


public class Db_handler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactManager",
            TABLE_CONTACTS = "contacts",
            KEY_ID = "_id",
            KEY_NAME = "name",
            KEY_PHONE = "phone",
            KEY_EMAIL = "email",
            KEY_ADDRESS = "address",
            KEY_IMAGEURI = "imageUri";

    private static final String TABLE_ADDRESS = "address";
    private static final String COLUMN_ID = "_id"; // convention
    private static final String COLUMN_TRIP_ADDRESS = "addr";
    private static final String COLUMN_TRIP_LATITUDE = "lati";
    private static final String COLUMN_TRIP_LONGITUDE = "longi";
    private static final String COLUMN_TRIP_ID = "trip_id";

    public Db_handler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_PHONE + " TEXT," + KEY_EMAIL + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_IMAGEURI + " TEXT)");

        db.execSQL("create table " + TABLE_ADDRESS + "(" + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_TRIP_ADDRESS + " varchar(200), " + COLUMN_TRIP_LATITUDE
                + " double, " + COLUMN_TRIP_LONGITUDE
                + " double, " + COLUMN_TRIP_ID
                + " integer references " + TABLE_CONTACTS + "(" + KEY_ID
                + "))" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(db);
    }

    public long createContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_IMAGEURI, contact.getImageURI().toString());
        return getWritableDatabase().insert(TABLE_CONTACTS, null, values);

    }

    public Contact getContact(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL, KEY_ADDRESS, KEY_IMAGEURI }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5)));
        db.close();
        cursor.close();
        return contact;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + "=?", new String[] { String.valueOf(contact.getId()) });
        db.close();
    }

    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_IMAGEURI, contact.getImageURI().toString());

        int rowsAffected = db.update(TABLE_CONTACTS, values, KEY_ID + "=?", new String[] { String.valueOf(contact.getId()) });
        db.close();

        return rowsAffected;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);

        if (cursor.moveToFirst()) {
            do {
                contacts.add(new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public List<String> getAllAddress() {
        List<String> addr = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT KEY_ADDRESS FROM " + TABLE_CONTACTS, null);

        if (cursor.moveToFirst()) {
            do {
                addr.add(cursor.getString(4));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return addr;
    }
    public long insertAddress(Address1 address1, long tripid) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TRIP_ADDRESS, address1.getAdd());
        cv.put(COLUMN_TRIP_LATITUDE, address1.getLat());
        cv.put(COLUMN_TRIP_LONGITUDE, address1.getLongi());
        cv.put(COLUMN_TRIP_ID, tripid);
        return getWritableDatabase().insert(TABLE_ADDRESS, null, cv);

    }

    public ArrayList<Double> lat() {
        Cursor cursor1 = null;
        String sql1 = "SELECT * FROM " + Db_handler.TABLE_ADDRESS;
        ArrayList<Double> lat1 = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor1 = db.rawQuery(sql1, null);
            for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1
                    .moveToNext()) {
                lat1.add(cursor1.getDouble(2));
            }
            cursor1.close();
            return lat1;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lat1;
    }

    public ArrayList<Double> longi() {
        Cursor cursor1 = null;
        String sql1 = "SELECT * FROM " + Db_handler.TABLE_ADDRESS;
        ArrayList<Double> lng1 = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor1 = db.rawQuery(sql1, null);
            for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1
                    .moveToNext()) {
                lng1.add(cursor1.getDouble(3));
            }
            cursor1.close();
            return lng1;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lng1;
    }

    public long getId(double lat, double lng){
        Cursor cursor2 = null;
        long tid = 0;
        String sql2 = "SELECT " + COLUMN_TRIP_ID + " FROM " + Db_handler.TABLE_ADDRESS
                + " WHERE " + COLUMN_TRIP_LATITUDE + "=" + lat + " AND " + COLUMN_TRIP_LONGITUDE + "=" + lng;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor2 = db.rawQuery(sql2,null);
            for (cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2
                    .moveToNext()) {
                tid = cursor2.getLong(0);
            }
            cursor2.close();
            return tid;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tid;

    }



    public Contact getOneContact(long tripid) {
        Cursor cursor1 = null;

        Contact contact = new Contact();


        String sql1 = "SELECT * FROM " + Db_handler.TABLE_CONTACTS
                + " WHERE " + KEY_ID + "=" + tripid;



        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor1 = db.rawQuery(sql1, null);
            for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1
                    .moveToNext()) {

                contact.set_name(cursor1.getString(1));
                contact.set_phone(cursor1.getString(2));
                contact.set_email(cursor1.getString(3));
                String img = cursor1.getString(5);
                contact.set_imageURI(Uri.parse(img));

            }

            cursor1.close();

            return contact;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contact;
    }

}

