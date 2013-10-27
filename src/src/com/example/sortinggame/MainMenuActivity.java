package com.example.sortinggame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenuActivity extends Activity {
	private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Prevent Screen From rotating
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        SoundManager.setContext(this);
        SoundManager.setMusic(R.raw.kalimba);
        SoundManager.playMusic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    //Handle Presses on the Action bar
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case R.id.mute:
            	SoundManager.setMuted(true);
            	return true;
            case R.id.sound:
            	disableSound();
            	return true;
            case R.id.orientation:
            	changeOrientation();
            	return true;
            default:
                return super.onOptionsItemSelected(item);
            }
    }
    
    public void disableSound(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Sound is currently " + SoundManager.checkSoundState() +
    				". Would you like to change it?").setTitle("Change Sound State");
    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			SoundManager.setMuted(true);
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
    
    public void changeOrientation(){
    	if (num==0){
    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    		num=1;
    		return;
    	}
    	else
    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		num=0;
    }
    
    public void loadLevelInterface(View view) {
            MediaPlayer clickSound = MediaPlayer.create(getBaseContext(), R.raw.back);
            clickSound.start();
            Intent intent = new Intent(this, LevelActivity.class);
            startActivity(intent);
    }
    public void loadCustomizerInterface(View view) {
            MediaPlayer clickSound = MediaPlayer.create(getBaseContext(), R.raw.back);
            clickSound.start();
            Intent intent = new Intent(this, CustomizerActivity.class);
            startActivity(intent);         
    }
}