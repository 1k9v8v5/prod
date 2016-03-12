package com.bask.prod;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
public class dbmanag implements Constants
{
	private static final int DB_VERSION = 7;
	
	private db db;
	private SQLiteDatabase base;
	private final Context context;
	public dbmanag(Context context){
		this.context = context;
	}
	// открыть подключение
	public void open() {
		db = new db(context, DB_NAME, null, DB_VERSION);
		base = db.getWritableDatabase();
	}
	// получить все данные из таблицы DB_TABLE
	public Cursor getAllData() {
		return base.query(DB_TABLE, null, null, null, null, null, null);
	}
}
