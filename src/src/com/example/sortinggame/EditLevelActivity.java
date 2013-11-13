package com.example.sortinggame;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class EditLevelActivity extends Activity {
	
	private static final int RESULT_LOAD_IMAGE = 1;
	String level_name ="";
	String cat1 ="";
	String cat2 ="";
	String cat3 ="";

	protected void onCreate(Bundle savedInstanceState) {

		// Prevent screen into landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_level);

		// Enable icon to function as back button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
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
	
	public void loadAddUpdateInterface(View view) {
		Intent intent = new Intent(this, AddUpdateActivity.class);
		startActivity(intent);
	}
	
	public void loadEditCategoryInterface(View view) {
		Intent intent = new Intent(this, EditCategoryActivity.class);
		startActivity(intent);
	}
	
	public void chooseFromGallery(View view){
		Intent intent = new Intent(
		Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, RESULT_LOAD_IMAGE);
		/*// View  the gallery
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		// Start the activity
		startActivity(intent);*/
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			System.out.println(picturePath);
			
		}
	}

}
