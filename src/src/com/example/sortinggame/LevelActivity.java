package com.example.sortinggame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class LevelActivity extends Activity {
	public final static String Icon_Position = "com.example.sortinggame.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //Prevent Screen From rotating
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);

	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	goToGameInterface(v, position);
//	            Toast.makeText(LevelActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level, menu);
		return true;
	}
	
	public void goBackToMain(View view){
		Intent intent = new Intent(this, MainMenuActivity.class);
    	startActivity(intent);
	}
	
	public void goToGameInterface(View view, int position){
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(Icon_Position, position);
    	startActivity(intent);
	}
	
}


