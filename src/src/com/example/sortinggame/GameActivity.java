package com.example.sortinggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent(); //Retrieve the intent
		
		//Retrieve the icon's position in the grid from the intent. 
		//position is the integer value of the icons position in the grid
		int position = intent.getIntExtra(LevelActivity.Icon_Position, -1);
		
		//Display Position for reference
		Toast.makeText(GameActivity.this, "" + position, Toast.LENGTH_LONG).show(); 
		
		setContentView(R.layout.activity_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
