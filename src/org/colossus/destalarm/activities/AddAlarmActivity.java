package org.colossus.destalarm.activities;

import java.util.List;

import org.colossus.destalarm.R;
import org.colossus.destalarm.dao.AlarmDao;
import org.colossus.destalarm.model.Alarm;
import org.colossus.destalarm.model.Destination;
import org.colossus.destalarm.util.Util;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.model.LatLng;

public class AddAlarmActivity extends Activity {
	private EditText addLabelText;
	private EditText addMapText;
	private AlarmDao alarmDao = null;
	private Alarm alarm = null;
	private Destination dest = null;

	// private Location location = null;
	// private String providerName = null;
	// private LocationManager locationManager = null;
	// private LocationListener locationListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alarm);

		addLabelText = (EditText) findViewById(R.id.addLabelText);
		addMapText = (EditText) findViewById(R.id.addMapText);
		alarmDao = new AlarmDao(this);

		alarm = new Alarm();

		Button addDoneBtn = (Button) findViewById(R.id.addDoneBtn);

		// locationListener = new LocationListener() {
		// @Override
		// public void onLocationChanged(Location loc) {
		// location = loc;
		// }
		//
		// @Override
		// public void onProviderDisabled(String provider) {
		//
		// }
		//
		// @Override
		// public void onProviderEnabled(String provider) {
		// }
		//
		// @Override
		// public void onStatusChanged(String provider, int status,
		// Bundle extras) {
		// }
		// };
		//
		// locationManager = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// 1000, 0, locationListener);
		//
		// location = locationManager
		// .getLastKnownLocation(LocationManager.GPS_PROVIDER);
		// providerName = LocationManager.GPS_PROVIDER;
		// if (location == null) {
		// location = locationManager
		// .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		// providerName = LocationManager.NETWORK_PROVIDER;
		// }

		// solve the android.os.NetworkOnMainThreadException
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork() // or
																		// .detectAll()
																		// for
																		// all
																		// detectable
																		// problems
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		addDoneBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// get data

				String label = addLabelText.getText().toString();

				alarm.setLabel(label);

				if (alarm.getDest() == null) {
					showDialog();
				} else {
					// save to db
					alarmDao.saveAlarm(alarm);

					// back to main activity
					Intent intent = new Intent();
					intent.setClass(AddAlarmActivity.this, MainActivity.class);
					startActivity(intent);
				}

			}
		});

		Button addCancelBtn = (Button) findViewById(R.id.addCancelBtn);

		addCancelBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(AddAlarmActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});

		ImageButton addMapSearchBtn = (ImageButton) findViewById(R.id.addMapSearchBtn);

		addMapSearchBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				String address = addMapText.getText().toString();
				if (Util.isStringBlank(address)) {
					showDialog();
				} else {

					List<Destination> locationList = Util
							.getLocationInfo(address);

					if (Util.isListBlank(locationList)) {
						showDialog();
					} else {
						// TODO:refactor later
						// now take the first location, later use map to locate
						dest = locationList.get(0);
						if (dest != null) {
							alarm.setDest(dest);

							addMapText.setText(dest.getAddress());

						}

					}

				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_alarm, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		// if (Util.isStringBlank(providerName)) {
		//
		// locationManager.requestLocationUpdates(providerName, 1000, 1,
		// locationListener);
		// }
	}

	@Override
	protected void onPause() {
		super.onPause();

		// if (locationManager != null) {
		// locationManager.removeUpdates(locationListener);
		// }
	}

	private void showDialog() {
		DialogFragment newFragment = MessageDialog
				.newInstance(getString(R.string.add_not_input_address));
		newFragment.show(getFragmentManager(), "dialog");
	}

}
