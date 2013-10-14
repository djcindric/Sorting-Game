package com.example.sortinggame;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class CustomizerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customizer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customizer, menu);
		return true;
	}
	
	public void dispatchTakePictureIntent(View view) {
    	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	startActivityForResult(takePictureIntent, 0);
    }
}