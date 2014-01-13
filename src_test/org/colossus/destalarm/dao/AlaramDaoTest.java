package org.colossus.destalarm.dao;

import java.util.List;

import junit.framework.Assert;

import org.colossus.destalarm.dao.AlarmDao;
import org.colossus.destalarm.model.Alarm;

import android.test.AndroidTestCase;

public class AlaramDaoTest extends AndroidTestCase {
	public void testListAlarmMaps(){
		String[] selectionArgs={};
		AlarmDao dao = new AlarmDao(getContext());
		List<Alarm> listAlarmMaps=dao.listAlarmMaps(selectionArgs);
		Assert.assertTrue(listAlarmMaps.size()>0);
		
	}
}
