package org.colossus.destalarm.dao;

import java.util.ArrayList;
import java.util.List;

import org.colossus.destalarm.model.Alarm;
import org.colossus.destalarm.model.Destination;
import org.colossus.destalarm.util.Constant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

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

		//newValues.put(Constant.ALARM_ID, alarm.getId());
		newValues.put(Constant.ALARM_LABEL, alarm.getLabel());
		Destination dest = alarm.getDest();
		LatLng latlng = dest.getLatlng();
		newValues.put(Constant.ALARM_LAT, latlng.latitude);
		newValues.put(Constant.ALARM_LNG, latlng.longitude);
		newValues.put(Constant.ALARM_ADDRESS, dest.getAddress());

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

	@SuppressWarnings("finally")
	public int updateALarm(Alarm alarm) {
		int affected = 0;

		ContentValues newValues = new ContentValues();

		newValues.put(Constant.ALARM_LABEL, alarm.getLabel());

		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			affected = database.update(Constant.ALARM_TABLE_NAME, newValues,
					Constant.ALARM_ID + "=" + alarm.getId(), null);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
			return affected;
		}
	}

	/**
	 * 
	 * @param selectionArgs
	 * @return
	 */
	public List<Alarm> listAlarmMaps(String[] selectionArgs) {
		List<Alarm> list = new ArrayList<Alarm>();
		String sql = "select " + Constant.ALARM_ID + "," + Constant.ALARM_LABEL+ "," 
				+ Constant.ALARM_ADDRESS+ ","  + Constant.ALARM_LAT+ "," 
				+ Constant.ALARM_LNG + " from " + Constant.ALARM_TABLE_NAME;
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, selectionArgs);
			if (cursor.moveToFirst()) {
				do {
					
					Alarm alarm = new Alarm();
					long id =cursor.getLong(0);
					String label = cursor.getString(1);					
					String address = cursor.getString(2);
					double lat = cursor.getDouble(3);
					double lng = cursor.getDouble(4);
					
					alarm.setId(id);
					alarm.setLabel(label);
					Destination dest = new Destination(new LatLng(lat,lng),address);
					alarm.setDest(dest);
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
			database.delete(Constant.ALARM_TABLE_NAME, Constant.ALARM_ID + "="
					+ id, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}

	}
}
