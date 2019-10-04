package com.mucil.developers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cart";
    static final String TABLE_NAME = "items";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String QUANTITY = "quantity";
    public static final String TOTAL = "total";

    

    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1); 
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE items (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price TEXT, quantity TEXT,total TEXT);");
    }

    @Override
    // Automatically called if database version number changes
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // bit mean to lose old data but at least we'll warn this is happening
        android.util.Log.w(this.getClass().getName(), DATABASE_NAME
                + " database upgrade to version " + newVersion
                + " old data lost");
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }

    public long insertDetails(String name, String price, String quantity, String total) {
        ContentValues rowValues = new ContentValues();

        // Assemble row of data in the ContentValues object
        rowValues.put(NAME, name);
        rowValues.put(PRICE, price);
        rowValues.put(QUANTITY, quantity);
        rowValues.put(TOTAL, total);

        return database.insertOrThrow(TABLE_NAME,null,rowValues);

    }
    
    public Cursor getAllRecords() {
    	return database.query(TABLE_NAME, null, null, null, null, null,
                NAME);
    }
    public void deletesingleRecord(String name,String price,String quantity,String total) {

		//database.delete(TABLE_NAME, NAME + " = ?", new String[] { name });
    	database.delete(TABLE_NAME,
    			NAME + "=? AND " + PRICE + "=? AND " +
    	        QUANTITY + "=? AND " + TOTAL + "=?",
    	        new String[] {name, price, quantity, total});
    	//database.execSQL("DELETE FROM items WHERE NAME = name");

		
	}
    public void deleteAllRecord() {

    		database.delete(TABLE_NAME,null,null);
    	

		
	}
   
}


    



