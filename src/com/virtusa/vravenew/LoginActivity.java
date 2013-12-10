package com.virtusa.vravenew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

public class LoginActivity extends Activity {
	private ProgressBar loginProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		loginProgress = (ProgressBar) findViewById(R.id.progressBarLogin);
		loginProgress.setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void login(View view){
		//loginProgress.setVisibility(View.VISIBLE);
		Intent vraveIntent = new Intent(getApplicationContext(),VRaveMainActivity.class);
		startActivity(vraveIntent);
		//loginProgress.setVisibility(View.GONE);
		overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
		finish();
	}

}
