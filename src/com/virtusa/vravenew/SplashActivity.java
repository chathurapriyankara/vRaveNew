package com.virtusa.vravenew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

public class SplashActivity extends Activity {
	private ProgressBar splashProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashProgress = (ProgressBar) findViewById(R.id.progressBarSplash);
        splashProgress.setVisibility(View.VISIBLE);
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//starat login after two seconds
				Intent loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
				startActivity(loginIntent);
				splashProgress.setVisibility(View.GONE);
				//apply transition animation
				overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
				finish();
			}
		},2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
