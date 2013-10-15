package com.example.sortinggame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;

public class CustomizerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //Prevent Screen From rotating
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customizer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customizer, menu);
		return true;
	}
	
	public void loadAddUpdateInterface(View view) {
    	Intent intent = new Intent(this, AddUpdateActivity.class);
    	startActivity(intent); 	
    }
	
	public void loadDeleteInterface(View view) {
    	Intent intent = new Intent(this, DeleteActivity.class);
    	startActivity(intent); 	
    }
	
	public void goBackToMain(View view){
		Intent intent = new Intent(this, MainMenuActivity.class);
    	startActivity(intent);
	}
}
