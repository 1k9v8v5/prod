package com.example.kv.prod;

/*import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/

import android.app.*;
import android.os.*;
import android.database.sqlite.*;
import android.database.Cursor;
import android.content.*;
import org.apache.http.*;
import android.widget.*;
import android.util.*;
import android.graphics.*;
import android.view.*;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import java.util.concurrent.TimeUnit;
import android.widget.AdapterView.*;
import android.widget.*;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.text.*;

public class MainActivity extends ListActivity implements Constants,LoaderCallbacks<Cursor>,AdapterView.OnItemSelectedListener
{
	//Cursor cursor;
	SimpleCursorAdapter	adapter;
	final String LOG_TAG = "log"; 
	long del_pos;
	private db dbcreate;
	private static String[] FROM = {COLUMN_ID,COLUMN_NAME};
	int[] TO ={R.id.rowid,R.id.text1};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);	

		dbcreate = new db(this);
		String dname =DB_NAME;
		String b=this.getDatabasePath(dname).getAbsolutePath();
		Log.d(LOG_TAG,b);	

		Cursor cursor = getProd();
		showProd(cursor);
		// добавляем контекстное меню к списку
		registerForContextMenu(getListView());
		getListView().setOnItemSelectedListener(this);
		getLoaderManager().initLoader(0,null,this);	
    }

	@Override
	public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
	{
		// TODO: Implement this method

	}

	@Override
	public void onNothingSelected(AdapterView<?> p1)
	{
		// TODO: Implement this method

	}




	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		// TODO: Implement this method
		super.onListItemClick(l, v, position, id);
		Toast.makeText(getApplicationContext(),
					   position+1 + " строка.",
					   Toast.LENGTH_SHORT).show();
		del_pos = id;		 
		/*TextView textView = (TextView) v;
		 String strText = textView.getText().toString(); // получаем текст нажатого элемента
		 */
		//del_pos = adapter.getItemId(position); 
		for(int a = 0; a < l.getChildCount(); a++)
		{
			l.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
		}
		v.setBackgroundColor(Color.GRAY);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 1, 0, R.string.del_record);
	}

	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// получаем из пункта контекстного меню данные по пункту списка
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item
				.getMenuInfo();
			// извлекаем id записи и удаляем соответствующую запись в БД
			delProd(acmi.id);
			// получаем новый курсор с данными
			getLoaderManager().getLoader(0).forceLoad();
			return true;
		}
		return super.onContextItemSelected(item);
	}
	public void onButtonClick1(View view)
	{
		addProd("beer");
		getLoaderManager().getLoader(0).forceLoad();
	}
	public void onButtonClick(View view)
	{
		delProd(del_pos);
		getLoaderManager().getLoader(0).forceLoad();
	}

	private void addProd(String name)
	{
		SQLiteDatabase base = dbcreate.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		base.insertOrThrow(DB_TABLE, null, values);
	}
	private void delProd(long id)
	{
		SQLiteDatabase base = dbcreate.getWritableDatabase();
		base.delete(DB_TABLE, COLUMN_ID + "=" + id, null);
	}
	private Cursor getProd()
	{
		SQLiteDatabase base = dbcreate.getReadableDatabase();
		Cursor cursor = base.query(DB_TABLE, FROM, null, null, null, null, null);

	
return cursor;
}
private void showProd(Cursor cursor)
{
/*
 while(cursor.moveToNext()) {
 int idColIndex = cursor.getColumnIndex("_id");
 int nameColIndex = cursor.getColumnIndex("name");	
 Log.d(LOG_TAG,"id = "+cursor.getString(idColIndex)+" | "+
 "name = " + cursor.getString(nameColIndex));
 }*/

adapter = new SimpleCursorAdapter(this, R.layout.item, cursor, FROM, TO);	
setListAdapter(adapter);


}
@Override
public void onLoadFinished(Loader<Cursor> p1, Cursor p2)
{
// TODO: Implement this method
adapter.swapCursor(p2);
}

@Override
public void onLoaderReset(Loader<Cursor> p1)
{
// TODO: Implement this method
}

@Override
public Loader<Cursor> onCreateLoader(int p1, Bundle p2)
{
// TODO: Implement this method
return new MyCursorLoader(this, dbcreate);
}
static class MyCursorLoader extends CursorLoader {

	db db;

	public MyCursorLoader(Context context, db db) {
		super(context);
		this.db = db;
	}

	@Override
	public Cursor loadInBackground() {
		SQLiteDatabase base = db.getReadableDatabase();
		Cursor cursor = base.query("product",FROM,null,null,null,null,null);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return cursor;
	}

	}

	protected void onDestroy() {
		super.onDestroy();
		// закрываем подключение при выходе
		dbcreate.close();
	}


}

