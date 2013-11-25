package com.example.sortinggame;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CustomizerControl {
	SortingDB db;
	
	public CustomizerControl( Context context) {
		db= new SortingDB(context);
	}
	
	public void saveLevel(String name, String icon, String background) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("iconPath", icon);
		values.put("background", background);
		db.insert("Level", null, values);
	}
	
	public void saveCategory(String level, int name, String icon) {
		ContentValues values = new ContentValues();
		values.put("levelName", level);
		values.put("categoryName", name);
		values.put("iconPath", icon);
		db.insert("Category", null, values);
	}
	
	public void saveImage(String path, int catID) {
		ContentValues values = new ContentValues();
		values.put("path", path);
		values.put("category_id", catID);
		db.insert("Images", null, values);
	}
	//Update seems to successfully run
	public void updateImage(String path, int catID) {
		ContentValues values = new ContentValues();
		String[] whereArgs = new String[] {String.valueOf(catID)};
		values.put("path", path);
		values.put("category_id", catID);
		db.update("Images", values, "category_id = ?"/*+ catID*/, whereArgs);
	}
	
	public void updateLevel(String name, String icon, String background) {
		ContentValues values = new ContentValues();
		String[] whereArgs = new String[] {String.valueOf(name)};
		values.put("name", name);
		values.put("iconPath", icon);
		values.put("background", background);
		db.update("Level", values,"name = ?" /*+ name*/, whereArgs);
	}
	
	public void updateCategory(String level, int name, String icon) {
		ContentValues values = new ContentValues();
		String[] whereArgs = new String[] {String.valueOf(level)};
		values.put("levelName", level);
		values.put("categoryName", name);
		values.put("iconPath", icon);
		db.update("Category", values, "levelName = ?" /*+ level*/, whereArgs);
	}
	
	public ArrayList<Integer> getCategoryIDs(String level) {
		ArrayList <Integer> ids = new ArrayList<Integer>();
		Cursor cursor = db.query("SELECT id FROM Category, Level WHERE Level.name = ? and Level.name = Category.levelName ORDER BY categoryName", new String[]{level});
		while(cursor.moveToNext()) {
			ids.add(cursor.getInt(0));
		}
		return ids;
	}
	
	public void deleteLevel(String name) {
		Cursor cursor = db.query("SELECT count(DISTINCT id) FROM Category, Level WHERE Level.name = ? and Level.name = Category.levelName ORDER BY categoryName", new String[]{name});
		String[] ids = new String[cursor.getCount()];
		cursor = db.query("SELECT id FROM Category, Level WHERE Level.name = ? and Level.name = Category.levelName ORDER BY categoryName", new String[]{name});
		
		for(int i = 0; i < ids.length; i++) {
			cursor.moveToNext();
			ids[i] = cursor.getString(0);
		}
		
		db.delete("Images", "category_id = ? or category_id = ? or category_id = ?" ,  ids);
		db.delete("Category", "levelName = ?", new String[]{name});
		db.delete("Level", "name = ?", new String[]{name});
	}
}