package com.example.sortinggame;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class GalleryActivity extends Activity {
	private ImageAdapterGallery imageAdapter;
	private Cursor cursor;
	private int columnIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		// Enable icon to function as back button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set up an array of the Thumbnail Image ID column we want
		String[] projection = { MediaStore.Images.Media._ID };
		// Create the cursor pointing to the SDCard
		cursor = getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, // Which columns to return
				null, // Return all rows
				null, MediaStore.Images.Media._ID);
		
		// Get the column index of the Thumbnails Image ID
		columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

		// Create a grid of icons for choosing a level
		GridView gridview = (GridView) findViewById(R.id.gallery);
		imageAdapter = new ImageAdapterGallery(this, cursor, columnIndex);
		gridview.setAdapter(imageAdapter);

		// Load Game Interface when icon is clicked
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Get the data location of the image
				String[] projection = { MediaStore.Images.Media.DATA };
				cursor = getContentResolver().query(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						projection, // Which columns to return
						null, // Return all rows
						null, null);
				columnIndex = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToPosition(position);
				// Get image filename
				String imagePath = cursor.getString(columnIndex);
				// Use this path to do further processing, i.e. full screen
				// display
				Toast.makeText(GalleryActivity.this, imagePath, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level, menu);
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
			MediaPlayer clickSound = MediaPlayer.create(getBaseContext(),
					R.raw.click);
			clickSound.start();
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void goBackToMain(View view) {
		Intent intent = new Intent(this, MainMenuActivity.class);
		startActivity(intent);
	}
}
