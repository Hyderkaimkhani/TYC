package com.example.hyder.tuc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by hv on 5/8/15 sqLiteDatabase App.
 */
public class LocalDatabase {

    private static final String dBaseName = "TYC";

    private static final int dBaseVersion = 1;

    private SQLiteDatabase sqLiteDatabase;

    private DatabaseHelper databaseHelper;


    public static final String Person_ROWID = "_id";
    public static final String Person_ID = "Person_ID";
    public static final String Person_email = "Email";
    public static final String Person_Fname = "Fname";
    public static final String Person_Lname = "Lname";
    public static final String Person_Company = "Company";
    public static final String Person_MgrID = "MgrID";
    public static final String Person_Rating = "Rating";
    public static final String Person_Password = "password";
    public static final String Person_Lat = "Loc_Lat";
    public static final String Person_Long = "Loc_Long";


    public static final String Task_ROWID = "_id";
    public static final String Task_Subject = "subject";
    public static final String Task_Summary = "summary";
    public static final String Task_AssignedBy = "assignedby";
    public static final String Task_AssignedTo = "assignedto";
    public static final String Task_Date = "startdate";
    public static final String Task_EndDate = "enddate";
    public static final String Task_Status = "status";
    public static final String Task_Lat = "task_Lat";
    public static final String Task_Long = "task_Long";



    private static final String TAG = "DBAdapter";
 //   private static final String DATABASE_NAME = "MyDB";
    private static final String PERSONS_TABLE = "persons";
    private static final String TASKS_TABLE = "tasks";
    private static final String IMAGE_TABLE = "image";
    //  private static final int DATABASE_VERSION = 1;

    public static final String KEY_IMAGE = "image_uri";


    private static final String CREATE_PERSONS_TABLE =
            "create table "+PERSONS_TABLE +" ("+ Person_ROWID +" text primary key, "
                    +Person_ID +" text ,"+ Person_email +" text not null,"+ Person_Fname +" text not null,"+ Person_Lname +
                    " text not null,"+Person_Company+" text,"+Person_MgrID+" text,"+Person_Rating+" text," +Person_Password+
                    " text,"+Person_Lat+" text,"+ Person_Long +" text,UNIQUE("+Person_email+") ON CONFLICT REPLACE)";

    private static final String CREATE_TASKS_TABLE =
            "create table "+TASKS_TABLE+" ("+ Task_ROWID +" text primary key, "
                    +Task_Subject +" text not null,"+ Task_Summary +" text not null,"+ Task_AssignedBy +" text not null,"+ Task_AssignedTo +
                    " text not null,"+Task_Date+" text,"+Task_EndDate+" text,"+Task_Status+" text," +Task_Lat+" text,"+ Task_Long +" text,UNIQUE("+Task_ROWID+") ON CONFLICT REPLACE)";



            /*"create table "+TASKS_TABLE +" ("+ KEY_ROWID +" integer primary key autoincrement, "
                    +Call_Sign +" text not null,"+ KEY_IMAGE +" text not null,UNIQUE("+Call_Sign+") ON CONFLICT REPLACE)";*/


/*

    private static final String driverTrail = "driverTrail",
            index = "Trailindex",
            latitude = "lat",
            longitude = "lon",
            time = "time";*/


    private final Context context;

    public LocalDatabase(Context context) {
        this.context = context;

        try {
            databaseHelper = new DatabaseHelper(context);
            //context.deleteDatabase(dBaseName);
        //    refreshTrailTable();
        }
        catch (NoClassDefFoundError ex)
        {
            Toast.makeText(context, "error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {}

    }

    public void openDatabase() {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public boolean isOpen() {

        return sqLiteDatabase.isOpen();
    }


    public void closeDatabase() {

        databaseHelper.close();
    }


    public long InsertEmployee (String id,String email,String Fname,String Lname, String Company,String MgrId,String Rating,String lat, String lon )
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Person_ROWID, id);
        initialValues.put(Person_email, email);
        initialValues.put(Person_Fname, Fname);
        initialValues.put(Person_Lname, Lname);
        initialValues.put(Person_Company, Company);
        initialValues.put(Person_MgrID, MgrId);
        initialValues.put(Person_Rating, Rating);
        initialValues.put(Person_Lat, lat);
        initialValues.put(Person_Long, lon);

        return sqLiteDatabase.insert(PERSONS_TABLE, null, initialValues);
    }

    public long InsertTask (String id,String subject,String summary,String assignedby, String assignedto,
                            String status,String date,String EndDate, String lat , String lon )
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Task_ROWID, id);
        initialValues.put(Task_Subject, subject);
        initialValues.put(Task_Summary, summary);
        initialValues.put(Task_AssignedBy, assignedby);
        initialValues.put(Task_AssignedTo, assignedto);
        initialValues.put(Task_Date, date);
        initialValues.put(Task_EndDate, EndDate);
        initialValues.put(Task_Status, status);
        initialValues.put(Task_Lat, lat);
        initialValues.put(Task_Long, lon);

        return sqLiteDatabase.insert(TASKS_TABLE, null, initialValues);
    }

    public Cursor getEmployees(String mgrid)
    {
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT Fname , Lname, Email FROM persons WHERE MgrID='"+mgrid+"' ",null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getProfile(String email)
    {
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT Fname , Lname, Email,Company,Rating FROM persons WHERE Email='"+email+"' ",null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


/*    public Cursor getJobDetail(String date, String callsign)
    {
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT date,time,destination,source,fare FROM account WHERE date='"+date+"' AND "+Call_Sign+"='"+callsign+"' ",null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }*/

/*    public Cursor getUniqueDate(String callsign) throws SQLException
    {*//*
        Cursor mCursor =db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_JOB,KEY_TO,KEY_FROM,KEY_FARE,KEY_DATE},
                KEY_ROWID + "=" + rowId, null,
                null, null, null, null);*//*
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT date,SUM(fare),count(*) FROM account WHERE "+Call_Sign+"='"+callsign+"' GROUP BY date",null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }*/

/*
    public void DeleteJob (String date )
    {
         sqLiteDatabase.rawQuery("DELETE FROM "+DATABASE_TABLE+" WHERE date <'"+date+"'",null);
    }
*/

/*
    public double Totalfare(String callsign)
    {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(fare) FROM "+DATABASE_TABLE+" WHERE "+Call_Sign+"='"+callsign+"' ",null);
        double totalfare = 0;

        if (cursor.moveToFirst())
        {
            totalfare = cursor.getDouble(0);
        }
        return totalfare;
    }
*/

    public int CountTasks(String callsign)
    {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT Count(*) FROM "+TASKS_TABLE+" WHERE "+Task_AssignedBy+"='"+callsign+ "' ",null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
  //      Log.d("no of rows :", ""+count);
        return count;
    }

    public long insertImage (String callsign, String imageuri)
    {
        ContentValues initialValues = new ContentValues();
        //   initialValues.put(KEY_JOB, job);
        initialValues.put(Person_email, callsign);
        initialValues.put(KEY_IMAGE, imageuri);
        //   sqLiteDatabase.re
        //  return sqLiteDatabase.insertWithOnConflict(DATABASE_TABLE, null, initialValues, SQLiteDatabase.CONFLICT_REPLACE);
        return sqLiteDatabase.insert(IMAGE_TABLE, null, initialValues);
    }

    public Cursor getImage(String callsign)
    {
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT "+KEY_IMAGE+" FROM "+IMAGE_TABLE+" WHERE "+Person_email+"='"+callsign+"'",null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


 /*   public void refreshTrailTable() {

        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE " + driverTrail);
        sqLiteDatabase.execSQL(CREATE_DRIVERTRAIL_TABLE);

    }*/

  //  private static final String[] DRIVER_TRAIL = new String[]{index, latitude, longitude, time};

/*
    private static final String CREATE_DRIVERTRAIL_TABLE = "create table "
            + driverTrail + " ("
            + index + " integer primary key autoincrement,"
            + latitude + " text not null,"
            + longitude + " text not null,"
            + time + " text not null)";
*/

/*    public void insertDriverTrail(String lat, String lng, String t) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(latitude, lat);
        contentValues.put(longitude, lng);
        contentValues.put(time, t);
        //return
                sqLiteDatabase.insert(driverTrail, null, contentValues);
    }*/

/*
    public Cursor getDriverTrail() {

        try {
            sqLiteDatabase = databaseHelper.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.query(true, driverTrail, DRIVER_TRAIL, null,
                    null, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                return cursor;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }
*/


    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, dBaseName, null, dBaseVersion);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            /*_db.execSQL(CREATE_USER_TABLE);
            _db.execSQL(CREATE_VEHICLE_TABLE);
            _db.execSQL(CREATE_DATA_TABLE);
            _db.execSQL(CREATE_DRIVER_TABLE);
            */

            try {
                //_db.execSQL("DROP TABLE IF EXISTS " + driverTrail);
                db.execSQL(CREATE_PERSONS_TABLE);
                db.execSQL(CREATE_TASKS_TABLE);
            } catch (Exception e) {

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            _db.execSQL("DROP TABLE IF EXISTS "+ PERSONS_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);
            onCreate(_db);

        }
    }

}