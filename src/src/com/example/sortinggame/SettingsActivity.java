package com.example.sortinggame;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsActivity extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		super.onCreate(savedInstanceState);
		
		//Use the settings xml file to display the preferences
		addPreferencesFromResource(R.xml.settings);
		
		//Make action bar icon function as back button.
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
	    //Set what happens when the change password preference is pressed
		Preference myPref = (Preference) findPreference("prefPassword");
		myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

	        @Override
	        public boolean onPreferenceClick(Preference preference) {
	        	passwordClick(); 
	            return false;
	        }
	    });
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	// Handle clicks on the action bar
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save:
			saveSettings();
			return true;
		case android.R.id.home:
    		NavUtils.navigateUpFromSameTask(this);
    		return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void passwordClick(){
    	final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	
    	if(prefs.getString("prefPassword", "NULL").equals("NULL")){
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage("You have not created a password yet.\n" +
        			"Would you like to create one now?").setTitle("Create Password");
        	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int id) {
        			createPassword();
        		}
        	});
        	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int id) {
        		}
        	});
        	AlertDialog dialog = builder.create();
        	dialog.show();
    	}
    	
    	else{
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage("Would you like to change your password?").setTitle("Change Password");
        	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int id) {
        			changePassword();
        		}
        	});
        	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int id) {
        		}
        	});
        	AlertDialog dialog = builder.create();
        	dialog.show();
    	}
	}
	
	//Prompt to enter a new password (For first time app is used)
	public void createPassword(){
		final EditText passwordView = new EditText(this);
    	final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	final SharedPreferences.Editor editor = prefs.edit();
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Enter a Password").setTitle("Create Password");
    	builder.setView(passwordView);
    	builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			String tempInput = passwordView.getText().toString();
    			editor.putString("prefPassword", tempInput);
    			editor.commit();
    			askAgain();
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show();
	}
	
	//Ask to confirm password (For creating a password)
	public void askAgain(){
    	final EditText passwordView = new EditText(this);
    	final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	final SharedPreferences.Editor editor = prefs.edit();
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Enter Password Again").setTitle("Confirm Password");
    	builder.setView(passwordView);
    	builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			String tempInput = passwordView.getText().toString();
    			if(tempInput.equals(prefs.getString("prefPassword", "NULL"))){
    				Toast.makeText(getApplicationContext(), "Password Saved", Toast.LENGTH_SHORT).show();
    			}
    			else{
    				Toast.makeText(getApplicationContext(), "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
    				editor.putString("prefPassword", "NULL");
	    			editor.commit();
	    			createPassword();
    			}
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			editor.putString("prefPassword", "NULL");
    			editor.commit();
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show(); 
    }
	
	//Prompt to enter previous password (For changing password)
	public void changePassword(){
		final EditText passwordView = new EditText(this);
    	final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Enter Previous Password").setTitle("Confirm Password");
    	builder.setView(passwordView);
    	builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			String tempInput = passwordView.getText().toString();
    			if(tempInput.equals(prefs.getString("prefPassword", "NULL"))){
    				changePassword2();
    			}
    			else{
    				Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
    				changePassword();
    			}
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show();
	}
	
	//Prompt to enter a new password (For changing password)
	public void changePassword2(){
		final EditText passwordView = new EditText(this);
    	final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	final SharedPreferences.Editor editor = prefs.edit();
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Enter New Password").setTitle("New Password");
    	builder.setView(passwordView);
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			String tempInput = passwordView.getText().toString();
    			editor.putString("tempPassword", tempInput);
    			editor.commit();
    			changePassword3();
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show();
	}
	
	//Prompt to reenter the new password (For changing password)
	public void changePassword3(){
		final EditText passwordView = new EditText(this);
    	final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	final SharedPreferences.Editor editor = prefs.edit();
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Confirm New Password").setTitle("New Password");
    	builder.setView(passwordView);
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			String tempInput = passwordView.getText().toString();
    			if(tempInput.equals(prefs.getString("tempPassword", "NULL"))){
    				Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_SHORT).show();
    				editor.putString("prefPassword", tempInput);
	    			editor.commit();
    			}
    			else{
    				Toast.makeText(getApplicationContext(), "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
	    			changePassword2();
    			}
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show();
	}
	
	//Apply any changes in the settings
	public void saveSettings() {
		SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(this);
		int i = getResources().getIdentifier(s.getString("prefMusicSelection", "NULL"), "raw", getPackageName());
		SoundManager.changeMusic(i);
		Toast.makeText(this, "Saving Settings...", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onBackPressed(){
		NavUtils.navigateUpFromSameTask(this);
	}
}
