package com.example.sortinggame;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class DeleteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Enable icon to function as back button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete, menu);
		return true;
	}
	
	//Handle clicks on the action bar
		public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    	case R.id.mute:
            	SoundManager.setMuted(true);
            	return true;
	    	case android.R.id.home:
	    		NavUtils.navigateUpFromSameTask(this);
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
	    	}
	    }

}
