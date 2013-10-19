package com.example.sortinggame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); // Prevent
																			// Screen
																			// From
																			// rotating
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		loadImages();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	// Handle Presses on the Action bar
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.hide_bar:
			ActionBar actionBar = getActionBar();
			actionBar.hide();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void loadLevelInterface(View view) {
		MediaPlayer clickSound = MediaPlayer.create(getBaseContext(),
				R.raw.back);
		clickSound.start();
		Intent intent = new Intent(this, LevelActivity.class);
		startActivity(intent);
	}

	public void loadCustomizerInterface(View view) {
		MediaPlayer clickSound = MediaPlayer.create(getBaseContext(),
				R.raw.back);
		clickSound.start();
		Intent intent = new Intent(this, CustomizerActivity.class);
		startActivity(intent);
	}

	// loops through array to save images
	public void loadImages() {
		Context context = getApplicationContext();

		// create folder
		File dir = new File(context.getFilesDir() + "/images/");

		// checks to see if path exists
		if (!dir.exists()) {
			//create folder if it does not exist
			dir.mkdirs();
			for (int i = 0; i < mThumbNames.length; i++)
				saveImage(mThumbNames[i], dir);
		}
	}

	// save image internally
	public void saveImage(String name, File dir) {
		Bitmap bitmap;
		OutputStream output;
		Resources r = getResources();

		// Retrieve the image from the res folder
		bitmap = BitmapFactory.decodeResource(getResources(),
				r.getIdentifier(name, "drawable", getPackageName()));

		// Create a name for the saved image
		File file = new File(dir, name + ".png");

		// Show a toast message on successful save
		Toast.makeText(MainMenuActivity.this, file.getAbsolutePath(),
				Toast.LENGTH_SHORT).show();

		try {

			output = new FileOutputStream(file);

			// Compress into png format image from 0% - 100%
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
			output.flush();
			output.close();
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Store the images to be saved internally
	private String[] mThumbNames = {
			// Sample images
			"albertosaurus", "bat3", "bear4", "bee3", "beta2", "black_widow",
			"bunny", "butterfly", "cardinal", "cat" };
}
