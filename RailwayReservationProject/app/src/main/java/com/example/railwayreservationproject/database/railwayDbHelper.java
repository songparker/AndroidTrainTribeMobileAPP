package com.example.railwayreservationproject.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.railwayreservationproject.Client;
import com.example.railwayreservationproject.database.railwayDbSchema.ClientTable;
import com.example.railwayreservationproject.database.railwayDbSchema.TrainTable;
import com.example.railwayreservationproject.database.railwayDbSchema.ScheduleTable;
import com.example.railwayreservationproject.database.railwayDbSchema.OrderTable;

public class railwayDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "railway.db";
    private static railwayDbHelper sInstance;

    public railwayDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static synchronized railwayDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new railwayDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Client table
        db.execSQL("CREATE TABLE " + railwayDbSchema.ClientTable.NAME + "(" +
                railwayDbSchema.ClientTable.Cols.CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                railwayDbSchema.ClientTable.Cols.EMAIL + " TEXT, " +
                railwayDbSchema.ClientTable.Cols.PASSWORD + " TEXT, " +
                railwayDbSchema.ClientTable.Cols.FIRSTNAME + " TEXT, " +
                railwayDbSchema.ClientTable.Cols.LASTNAME + " TEXT, " +
                railwayDbSchema.ClientTable.Cols.MOBILE + " TEXT" +
                ")");

        // Create the Train table
        db.execSQL("CREATE TABLE " + railwayDbSchema.TrainTable.NAME + "(" +
                railwayDbSchema.TrainTable.Cols.TRAIN_ID + " INTEGER PRIMARY KEY, " +
                railwayDbSchema.TrainTable.Cols.ORIGIN + " TEXT, " +
                railwayDbSchema.TrainTable.Cols.DESTINATION + " TEXT, " +
                railwayDbSchema.TrainTable.Cols.ECONOMY_PRICE + " REAL, " +
                railwayDbSchema.TrainTable.Cols.BUSINESS_PRICE + " REAL" +
                ")");

        // Create the Schedule table
        db.execSQL("CREATE TABLE " + railwayDbSchema.ScheduleTable.NAME + "(" +
                railwayDbSchema.ScheduleTable.Cols.SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                railwayDbSchema.ScheduleTable.Cols.TRAIN_ID + " INTEGER, " +
                railwayDbSchema.ScheduleTable.Cols.DEPARTURE_DATE + " TEXT, " +
                railwayDbSchema.ScheduleTable.Cols.DEPARTURE_TIME + " TEXT, " +
                railwayDbSchema.ScheduleTable.Cols.ARRIVAL_DATE + " TEXT, " +
                railwayDbSchema.ScheduleTable.Cols.ARRIVAL_TIME + " TEXT, " +
                railwayDbSchema.ScheduleTable.Cols.ECO_AVAILABILITY + " INTEGER, " +
                railwayDbSchema.ScheduleTable.Cols.BUSI_AVAILABILITY + " INTEGER, " +
                railwayDbSchema.ScheduleTable.Cols.DURATION + " TEXT, " +
                "UNIQUE(" + railwayDbSchema.ScheduleTable.Cols.TRAIN_ID + ", " +
                railwayDbSchema.ScheduleTable.Cols.DEPARTURE_DATE + ", " +
                railwayDbSchema.ScheduleTable.Cols.DEPARTURE_TIME + ")" +
                ", FOREIGN KEY(" + railwayDbSchema.ScheduleTable.Cols.TRAIN_ID + ") REFERENCES " +
                railwayDbSchema.TrainTable.NAME + "(" + railwayDbSchema.TrainTable.Cols.TRAIN_ID + ")" +
                ")");

        // Create the Order table
        db.execSQL("CREATE TABLE " + railwayDbSchema.OrderTable.NAME + "(" +
                railwayDbSchema.OrderTable.Cols.ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                railwayDbSchema.OrderTable.Cols.CLIENT_ID + " INTEGER, " +
                railwayDbSchema.OrderTable.Cols.TRAIN_ID + " INTEGER, " +
                railwayDbSchema.OrderTable.Cols.SCHEDULE_ID + " INTEGER, " +
                railwayDbSchema.OrderTable.Cols.ECO_NUMBER + " INTEGER, " +
                railwayDbSchema.OrderTable.Cols.BUSI_NUMBER + " INTEGER, " +
                railwayDbSchema.OrderTable.Cols.TOTAL_PRICE + " REAL, " +
                "FOREIGN KEY(" + railwayDbSchema.OrderTable.Cols.CLIENT_ID + ") REFERENCES " +
                railwayDbSchema.ClientTable.NAME + "(" + railwayDbSchema.ClientTable.Cols.CLIENT_ID + ") ON DELETE CASCADE," +
                "FOREIGN KEY(" + railwayDbSchema.OrderTable.Cols.TRAIN_ID + ") REFERENCES " +
                railwayDbSchema.TrainTable.NAME + "(" + railwayDbSchema.TrainTable.Cols.TRAIN_ID + ") ON DELETE CASCADE," +
                "FOREIGN KEY(" + railwayDbSchema.OrderTable.Cols.SCHEDULE_ID + ") REFERENCES " +
                railwayDbSchema.ScheduleTable.NAME + "(" + railwayDbSchema.ScheduleTable.Cols.SCHEDULE_ID + ") ON DELETE CASCADE" +
                ")");
    }

    public Client getClientByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ClientTable.NAME, null, ClientTable.Cols.EMAIL + "=?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
           String id = "";
            int idColumnIndex = cursor.getColumnIndex(ClientTable.Cols.FIRSTNAME);
            if (idColumnIndex >= 0) {
                id = cursor.getString(idColumnIndex);
            }

            String password = "";
            int passwordColumnIndex = cursor.getColumnIndex(ClientTable.Cols.PASSWORD);
            if (passwordColumnIndex >= 0) {
                password = cursor.getString(passwordColumnIndex);
            }

            String firstName = "";
            int firstNameColumnIndex = cursor.getColumnIndex(ClientTable.Cols.FIRSTNAME);
            if (firstNameColumnIndex >= 0) {
                firstName = cursor.getString(firstNameColumnIndex);
            }

            String lastName = "";
            int lastNameColumnIndex = cursor.getColumnIndex(ClientTable.Cols.LASTNAME);
            if (lastNameColumnIndex >= 0) {
                lastName = cursor.getString(lastNameColumnIndex);
            }

            String mobile = "";
            int mobileColumnIndex = cursor.getColumnIndex(ClientTable.Cols.MOBILE);
            if (mobileColumnIndex >= 0) {
                mobile = cursor.getString(mobileColumnIndex);
            }

            Client client = new Client(id, email, password, firstName, lastName, mobile);

            cursor.close();
            db.close();
            return client;
        } else {
            db.close();
            return null;
        }
    }

    public boolean updateClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ClientTable.Cols.FIRSTNAME, client.getFirstName());
        values.put(ClientTable.Cols.LASTNAME, client.getLastName());
        values.put(ClientTable.Cols.MOBILE, client.getMobile());

        int rowsAffected = db.update(ClientTable.NAME, values, ClientTable.Cols.EMAIL + "=?", new String[]{client.getEmail()});

        db.close();
        return rowsAffected > 0;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables
        db.execSQL("DROP TABLE IF EXISTS " + ScheduleTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrainTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClientTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OrderTable.NAME);

        // Create the new tables
        onCreate(db);
    }
}
