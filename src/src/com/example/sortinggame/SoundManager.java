package com.example.sortinggame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

//MediaPlayers are referenced by position in the array
//0 - Menu Music
//1 - GameWin Music


public class SoundManager extends Activity {
	static int numPlayers = 2;
	static MediaPlayer[] players = new MediaPlayer[2];
	static Boolean[] isMuted = new Boolean[2];
	
	static Context context;
	
	static boolean isInitialized=false;
	
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); //Obtain reference to preferences
    SharedPreferences.Editor editor = prefs.edit(); 
	
	public static void setContext(Context cont){
		context = cont;
	}
	
	public static void initializePlayers(int i){
		
		if(isInitialized==false){
			if(i != 0){
				players[0] = MediaPlayer.create(context, i);
			}
			else{
				players[0] = MediaPlayer.create(context, R.raw.kalimba);
			}
			players[1] = MediaPlayer.create(context, R.raw.gamewin);
			players[0].setLooping(true);
			isInitialized=true;
			isMuted[0] = false;
		}
	}
	
	public static void changeMusic(int i){
		players[0].release();
		players[0] = MediaPlayer.create(context, i);
		players[0].setLooping(true);
	}
	
	public static void playMusic(int playerNumber){
		if(players[playerNumber].isPlaying()){
			players[playerNumber].pause();
			isMuted[playerNumber]=true;
		}
		else{
			players[playerNumber].start();
			isMuted[playerNumber] = false;
		}
	}
	public static void stopPlayer(int playerNumber){
		players[playerNumber].stop();
	}
	
	public static String checkSoundState(int playerNumber){
		if (isMuted[playerNumber]){
			return "off";
		}
		return "on";
	}
	
	public static void muteAll(){
		for (int i=0; i < numPlayers; i++){
			if(players[i].isPlaying()){
				players[i].pause();
			}
			isMuted[i]=true;
		}
	}
	
	public static void releasePlayer(int playerNumber){
		players[playerNumber].release();
	}
	
	public static void quit(){
		for(int i=0; i < numPlayers; i++){
			players[i].release();
		}
	}
}