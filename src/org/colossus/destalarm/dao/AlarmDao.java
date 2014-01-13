package org.colossus.destalarm.dao;

import java.util.ArrayList;
import java.util.List;

import org.colossus.destalarm.model.Alarm;
import org.colossus.destalarm.util.Constant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AlarmDao {

	private DbOpenHelper helper = null;

	public AlarmDao(Context context) {
		helper = new DbOpenHelper(context);
	}

	/**
	 * 
	 * @param alarm
	 */
	public void saveAlarm(Alarm alarm) {

		ContentValues newValues = new ContentValues();
		
		newValues.put("id", alarm.getId());
		newValues.put("label", alarm.getLabel());

		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.insert(Constant.ALARM_TABLE_NAME, null, newValues);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
	}
	
	public void updateALarm(Alarm alarm){
		
		ContentValues newValues = new ContentValues();
		
		
		newValues.put("label", alarm.getLabel());
		
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			int affect = database.update(Constant.ALARM_TABLE_NAME, newValues, Constant.ALARM_ID+"="+alarm.getId(), null);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
	}

	/**
	 * 
	 * @param selectionArgs
	 * @return
	 */
	public List<Alarm> listAlarmMaps(String[] selectionArgs) {
		List<Alarm> list = new ArrayList<Alarm>();
		String sql = "select " + Constant.ALARM_ID + "," + Constant.ALARM_LABEL
				+ " from " + Constant.ALARM_TABLE_NAME;
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, selectionArgs);
			if (cursor.moveToFirst()) {
				do {
					Alarm alarm = new Alarm();
					alarm.setId(cursor.getLong(0));
					alarm.setLabel(cursor.getString(1));
					// Adding contact to list
					list.add(alarm);
				} while (cursor.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return list;
	}

	/**
	 * 
	 * @param id
	 */
	public void deletAlarm(long id) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.delete(Constant.ALARM_TABLE_NAME,
					Constant.ALARM_ID + "=" + id, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}

	}
}
