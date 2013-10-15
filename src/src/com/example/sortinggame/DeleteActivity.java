package com.example.sortinggame;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class DeleteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Remove Icon from Action Bar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		
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
	    	case R.id.hide_bar:
	    		ActionBar actionBar = getActionBar();
	    		actionBar.hide();
	    		return true;
	    	case R.id.go_back:
	    		Intent intent = new Intent(this, CustomizerActivity.class);
	        	startActivity(intent);
	    	default:
	    		return super.onOptionsItemSelected(item);
	    	}
	    }

}
