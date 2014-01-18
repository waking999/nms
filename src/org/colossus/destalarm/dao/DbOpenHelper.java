package org.colossus.destalarm.dao;

import org.colossus.destalarm.util.Constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {


	public DbOpenHelper(Context context) {
		super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "CREATE TABLE " + Constant.ALARM_TABLE_NAME + " ( " 
				+ Constant.ALARM_ID		+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ Constant.ALARM_LABEL	+ " varchar(100), " 
				+ Constant.ALARM_LAT +" Real,"
				+ Constant.ALARM_LNG +" Real,"
				+ Constant.ALARM_ADDRESS +" varchar(300)"				
				+ ")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}