package com.virtusa.vravenew;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class BadgeDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_badge_detail);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DC8909")));
		actionBar.setTitle("View Badges");
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Intent myIntent= getIntent(); // gets the previously created intent
		String badgeName = myIntent.getStringExtra("BagdeName");
		String badgeDescript = myIntent.getStringExtra("Description");
		
		TextView name = (TextView) findViewById(R.id.BadgeTitle);
		TextView description = (TextView) findViewById(R.id.BadgeDescription);
		
		name.setText(badgeName);
		description.setText(badgeDescript);
		
		ImageView ivcatImg = (ImageView) findViewById(R.id.badge);
		
		if(badgeName.equals("Avenger")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.avenger));
		}
		else if(badgeName.equals("Engizer")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.engizer));
		}
		else if(badgeName.equals("Hulk")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.hulk));
		}
		else if(badgeName.equals("Rave")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.rave));
		}
		else if(badgeName.equals("Rockstar")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.rockstar));
		}
		else if(badgeName.equals("VIP")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.vip));
		}
		else if(badgeName.equals("Wizard of Bugs")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.wizardofbugs));
		}
		else if(badgeName.equals("Einstien")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.einstien));
		}
		else if(badgeName.equals("Coach")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.coach));
		}
		else if(badgeName.equals("Code Ninja")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.code_ninja));
		}
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		
//
//		Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.badge_detail, menu);
//		return true;
//	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent settingsIntent = new Intent(BadgeDetail.this,
					SettingsActivity.class);
			startActivity(settingsIntent);
			return true;
		case android.R.id.home:
			finish();
			// NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
