package org.colossus.destalarm.activities;

import java.util.List;

import org.colossus.destalarm.R;
import org.colossus.destalarm.dao.AlarmDao;
import org.colossus.destalarm.model.Alarm;
import org.colossus.destalarm.util.Constant;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView listView;
	private ArrayAdapter<Alarm> adapter;
	private AlarmDao alarmDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		alarmDao = new AlarmDao(this);

		List<Alarm> data = alarmDao.listAlarmMaps(null);
		listView = (ListView) findViewById(R.id.mainListView);

		adapter = new ArrayAdapter<Alarm>(MainActivity.this,
				android.R.layout.simple_list_item_single_choice, data);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setAdapter(adapter);

		Button mainAddBtn = (Button) findViewById(R.id.mainAddBtn);
		mainAddBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AddAlarmActivity.class);
				startActivity(intent);
			}
		});

		Button mainEditBtn = (Button) findViewById(R.id.mainEditBtn);
		mainEditBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int position = listView.getCheckedItemPosition();
				if(position!=-1){
					Alarm alarm = (Alarm) adapter.getItem(position);
					Bundle bundle = new Bundle();
					bundle.putLong(Constant.ALARM_ID, alarm.getId());
					bundle.putString(Constant.ALARM_LABEL, alarm.getLabel());
					
					Intent intent = new Intent();
					intent.putExtras(bundle);
					intent.setClass(MainActivity.this, EditAlarmActivity.class);
					startActivity(intent);
				}else{
			
				    showDialog(); 
				}
				
			}

			
		});
		
		
		Button mainDelBtn = (Button) findViewById(R.id.mainDelBtn);
		mainDelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int position = listView.getCheckedItemPosition();
				if(position!=-1){
					Alarm alarm = (Alarm) adapter.getItem(position);
					long id = alarm.getId();

					// del item
					alarmDao.deletAlarm(id);

					Intent intent = new Intent();
					intent.setClass(MainActivity.this, MainActivity.class);
					startActivity(intent);
				}else{
					showDialog(); 
				}
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void showDialog() {
		DialogFragment newFragment = MessageDialog.newInstance(getString(R.string.main_not_select_msg));
		newFragment.show(getFragmentManager(), "dialog");
	}

	
	
}


