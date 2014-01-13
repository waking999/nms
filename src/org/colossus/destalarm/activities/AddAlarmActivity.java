package org.colossus.destalarm.activities;

import org.colossus.destalarm.R;
import org.colossus.destalarm.dao.AlarmDao;
import org.colossus.destalarm.model.Alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddAlarmActivity extends Activity {
	private EditText addLabelText;
	private AlarmDao alarmDao = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alarm);

		addLabelText = (EditText) findViewById(R.id.addLabelText);
		alarmDao = new AlarmDao(this);

		Button addDoneBtn = (Button) findViewById(R.id.addDoneBtn);

		addDoneBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// get data

				String label = addLabelText.getText().toString();

				Alarm alarm = new Alarm();
				alarm.setLabel(label);
				// save to db

				alarmDao.saveAlarm(alarm);

				// back to main activity
				Intent intent = new Intent();
				intent.setClass(AddAlarmActivity.this, MainActivity.class);
				startActivity(intent);

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_alarm, menu);
		return true;
	}

}
