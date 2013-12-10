package com.virtusa.vravenew;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AddNewRaveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_rave);
		
		final ProgressBar PbSendrave = (ProgressBar) findViewById(R.id.progressBarSendRave);
		PbSendrave.setVisibility(View.GONE);
		
		final ImageView sendNewRave = (ImageView) findViewById(R.id.imageView4);
		sendNewRave.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendNewRave.setVisibility(View.GONE);
					PbSendrave.setVisibility(View.VISIBLE);
				}
				
				return false;
			}
		});
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_rave, menu);
		return true;
	}
	

}
