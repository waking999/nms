package org.colossus.destalarm.activities;

import org.colossus.destalarm.R;
import org.colossus.destalarm.dao.AlarmDao;
import org.colossus.destalarm.model.Alarm;
import org.colossus.destalarm.util.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditAlarmActivity extends Activity {
	private EditText editLabelText;
	private AlarmDao alarmDao = null;
	private long alarmId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_alarm);

		editLabelText = (EditText) findViewById(R.id.editLabelText);
		alarmDao = new AlarmDao(this);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		editLabelText.setText(bundle.getString(Constant.ALARM_LABEL));
		alarmId = bundle.getLong(Constant.ALARM_ID);
		
		Button editDoneBtn = (Button) findViewById(R.id.editDoneBtn);

		editDoneBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// get data

				String label = editLabelText.getText().toString();

				Alarm alarm = new Alarm();
				alarm.setId(alarmId);
				alarm.setLabel(label);
				// save to db

				alarmDao.updateALarm(alarm);

				// back to main activity
				Intent intent = new Intent();
				intent.setClass(EditAlarmActivity.this, MainActivity.class);
				startActivity(intent);

			}
		});

		Button editCancelBtn = (Button) findViewById(R.id.editCancelBtn);

		editCancelBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(EditAlarmActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_alarm, menu);
		return true;
	}

}
