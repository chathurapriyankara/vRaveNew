package com.virtusa.vravenew;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

public class VRaveMainActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vrave_main);
		final TabHost tabs= (TabHost) findViewById(R.id.tabhost);
		tabs.setup();
		
		TabHost.TabSpec spec=tabs.newTabSpec("tag1");
		spec.setContent(R.id.fragment1);
		spec.setIndicator("View Raves");
		tabs.addTab(spec);
		spec=tabs.newTabSpec("tag2");
		spec.setContent(R.id.fragment2);
		spec.setIndicator("My Badges");
		tabs.addTab(spec);
		tabs.setCurrentTab(0);
		
		final ImageView ivAddNewRave = (ImageView) findViewById(R.id.imageView4);
		ivAddNewRave.setOnTouchListener(new OnTouchListener() {
			 
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int clickEvent = event.getAction();
				if(clickEvent==MotionEvent.ACTION_DOWN){
					ivAddNewRave.setColorFilter(Color.rgb(51, 181, 229));
					Intent intent  = new Intent(VRaveMainActivity.this,AddNewRaveActivity.class);
					startActivity(intent);
				}
				
				if(event.getAction()==MotionEvent.ACTION_UP){
					ivAddNewRave.setColorFilter(Color.WHITE);
				}
				
				
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vrave_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	Intent settingsIntent = new Intent(VRaveMainActivity.this,SettingsActivity.class);
	        	startActivity(settingsIntent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	

}
