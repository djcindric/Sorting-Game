package com.example.sortinggame;

import java.util.ArrayList;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class GalleryActivity extends Activity {
	private ImageAdapterGallery imageAdapter;
	private Cursor cursor;
	private int columnIndex;
	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		// Enable icon to function as back button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		initImageLoader();
		
		getGalleryImages();

		// Create a grid of icons for choosing a level
		GridView gridview = (GridView) findViewById(R.id.gallery);
		gridview.setFastScrollEnabled(true);
		imageAdapter = new ImageAdapterGallery(this, cursor, columnIndex, imageLoader);
		gridview.setAdapter(imageAdapter);
	}
	
	protected void onStart(){
    	super.onStart();
    	
    	//Start menu music
    	if(SoundManager.isMuted[0] == false){
    		SoundManager.playMusic(0);
    	}
    	
    }

    protected void onPause(){
    	super.onPause();
    	//Pause music when app is out of focus
    	if(SoundManager.players[0].isPlaying()){
    		SoundManager.playMusic(0);
    		SoundManager.isMuted[0] = false;
    	}
    }
	
	private void initImageLoader() {
        DisplayImageOptions defaultOptions =  new DisplayImageOptions.Builder()
        .showStubImage(R.drawable.ic_stub)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions).memoryCache(
                new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }
	
	private void getGalleryImages() {
		// Set up an array of the Thumbnail Image ID column we want
				String[] projection = { MediaStore.Images.Thumbnails.DATA };
				// Create the cursor pointing to the SDCard
				cursor = getContentResolver().query(
						MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, // Which columns to return
						null, // Return all rows
						null, MediaStore.Images.Thumbnails.DATA);
				
				// Get the column index of the Thumbnails Image ID
				columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
	}
	
public void btnChoosePhotosClick(View v){	
		ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
		Toast.makeText(GalleryActivity.this, "Total photos selected: "+selectedItems.size(), Toast.LENGTH_SHORT).show();
		Log.d(GalleryActivity.class.getSimpleName(), "Selected Items: " + selectedItems.toString());
		String[] allPath = new String[selectedItems.size()];
        for (int i = 0; i < allPath.length; i++) {
                allPath[i] = selectedItems.get(i);
        }
		Intent data = new Intent().putExtra("image_paths", allPath);
	    setResult(RESULT_OK, data);
	    finish();
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
		case R.id.mute:
        	SoundManager.playMusic(0);
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
