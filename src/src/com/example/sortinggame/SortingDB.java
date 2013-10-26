package com.example.sortinggame;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;



public class SortingDB extends SQLiteAssetHelper
{
	private static final String DATABASE_NAME = "sorting";
	private static final int DATABASE_VERION = 1;
	
	
	public SortingDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERION);
		// TODO Auto-generated constructor stub
	}
	
	public Cursor query()
	{
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM level", null);
		return c;
	}
	
}
