package com.virtusa.vravenew;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class RaveDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rave_detail);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DC8909")));
		actionBar.setTitle("View Raves");
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setTitle("RA\u2764E");
		
		Intent myIntent= getIntent(); // gets the previously created intent
		String raveTitle = myIntent.getStringExtra("rave_title");
		String raveMsg = myIntent.getStringExtra("rave_msg"); 
		String ravePerson = myIntent.getStringExtra("rave_person");
		TextView tvRaveTitle = (TextView) findViewById(R.id.textViewRaveTitle);
		TextView tvRaveMsg = (TextView) findViewById(R.id.textViewRaveMessage);
		TextView tvRavePerson = (TextView) findViewById(R.id.textViewRavePerson);
		
		tvRaveTitle.setText(raveTitle);
		tvRaveMsg.setText(raveMsg);
		tvRavePerson.setText(ravePerson);
		ImageView ivcatImg = (ImageView) findViewById(R.id.imageViewRaveCatImage);
		if(raveTitle.equals("Thanks for your help")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.thanks_for_your_help));
		}
		else if(raveTitle.equals("You are a role model")){
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.pirl));
		}
		else if (raveTitle.equals("Awesome code")) {
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.killer_code));
		}
		else if (raveTitle.equals("Sharp testing")) {
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.eagle_eye));
		}
		else if (raveTitle.equals("Good job")) {
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.great_work));
		}
		else if (raveTitle.equals("Thanks for your advice")) {
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.great_advice));
		}
		else if (raveTitle.equals("Good thinking")) {
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.great_idea));
		}
		else if (raveTitle.equals("Thanks for the great service")) {
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.super_service));
		}
		else {
			ivcatImg.setImageDrawable(getResources().getDrawable(R.drawable.old_raves_default));
		}
				
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.rave_description, menu);
//		return true;
//	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent settingsIntent = new Intent(RaveDetailActivity.this,
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
