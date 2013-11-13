package com.example.sortinggame;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class EditCategoryActivity extends Activity{
	private String level;
	
	protected void onCreate(Bundle savedInstanceState) {

		// Forces Screen into landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_category);

		// Enable icon to function as back button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		level = i.getExtras().getString(CustomizerActivity.LEVEL_NAME);
		
		actionBar.setTitle("Add Images to " + level);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customizer, menu);
		return true;
	}

	// Handle clicks on the action bar
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.hide_bar:
			ActionBar actionBar = getActionBar();
			actionBar.hide();
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void loadEditLevelInterface(View view) {
		Intent intent = new Intent(this, EditLevelActivity.class);
		startActivity(intent);
	}
	
	public void chooseFromGallery(View view){
		Intent intent = new Intent(this, GalleryActivity.class);
		// View  the gallery
		//intent.setAction(Intent.ACTION_VIEW);
		//intent.setData(android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		// Start the activity
		startActivity(intent);
	}

}
