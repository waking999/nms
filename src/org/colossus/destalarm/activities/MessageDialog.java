package org.colossus.destalarm.activities;

import org.colossus.destalarm.R;
import org.colossus.destalarm.util.Constant;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageDialog extends DialogFragment {

	public static MessageDialog newInstance(String msg) {

		MessageDialog frag = new MessageDialog();
		Bundle args = new Bundle();
		args.putString(Constant.MESSAGE_KEY, msg);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_message, container, false);
		View tv = v.findViewById(R.id.msgText);
		String msg = getArguments().getString(Constant.MESSAGE_KEY);

		((TextView) tv).setText(msg);
		
		Button msgCfmBtn = (Button) v.findViewById(R.id.msgCfmBtn);  
		msgCfmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();  
			}
		});

		return v;
	}
}