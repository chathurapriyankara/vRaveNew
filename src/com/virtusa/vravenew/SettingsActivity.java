package com.virtusa.vravenew;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DC8909")));
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("SETTINGS");
		
		ListView settingItems = (ListView) findViewById(R.id.listViewSettingsMenu);
		String[] settingsMenu = {"Logout","Help","About"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.settings_list_item,R.id.textViewSttingsItem,settingsMenu);
		settingItems.setAdapter(adapter);
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish();
	            //NavUtils.navigateUpFromSameTask(this);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
