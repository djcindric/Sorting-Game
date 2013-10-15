package com.example.sortinggame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    public void loadLevelInterface(View view) {
    	MediaPlayer laserSound = MediaPlayer.create(getBaseContext(), R.raw.laser);
    	laserSound.start();
    	Intent intent = new Intent(this, LevelActivity.class);
    	startActivity(intent);
    }
    
    public void loadCustomizerInterface(View view) {
    	MediaPlayer laserSound = MediaPlayer.create(getBaseContext(), R.raw.laser);
    	laserSound.start();
    	Intent intent = new Intent(this, CustomizerActivity.class);
    	startActivity(intent); 	
    }
}
