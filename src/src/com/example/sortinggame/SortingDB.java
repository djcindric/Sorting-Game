package com.example.sortinggame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;



public class SortingDB extends SQLiteAssetHelper {
	private static final String DATABASE_NAME = "sorting";
	private static final int DATABASE_VERION = 1;
	
	
	public SortingDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERION);
		// TODO Auto-generated constructor stub
	}

	//returns a Cursor object that contains the results of query
	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = getReadableDatabase();
		return db.rawQuery(sql, args);
	}
	
	//returns a Cursor object that contains the results of query
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		SQLiteDatabase db = getReadableDatabase();
		return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	
	//returns the row id of the newly inserted row or -1 if an error occured
	public long insert (String table, String nullColumnHack, ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		return db.insert(table, nullColumnHack, values);
	}
	//returns the number of rows affected
	public int update (String table, ContentValues values, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		return db.update (table, values, whereClause, whereArgs);		
	}
	
	//returns the number of rows affected 
	public int delete (String table, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		return db.delete(table, whereClause, whereArgs);
	}
}
