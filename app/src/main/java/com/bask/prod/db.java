package com.bask.prod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class db extends SQLiteOpenHelper implements Constants

{
	public db(Context context, String name, CursorFactory factory,
					int version) {
		super(context, name, factory, version);
    }
	public db (Context context){
		super(context,DB_NAME,null,11);
	}
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		// TODO: Implement this method
		p1.execSQL("create table "+ DB_TABLE + " ("
				   + COLUMN_ID + " integer primary key autoincrement, " 
				   + COLUMN_NAME + " text" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		// TODO: Implement this method
		db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
		onCreate(db);
	}


				
	
}
