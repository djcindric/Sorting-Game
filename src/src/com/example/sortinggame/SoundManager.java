package com.example.sortinggame;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

//MediaPlayers are referenced by position in the array
//0 - Menu Music
//1 - Click
//2 - GameWin Music
//3 - Game Music


public class SoundManager extends Activity {
	static int numPlayers = 4;
	static MediaPlayer[] players = new MediaPlayer[4];
	static Boolean[] isMuted = new Boolean[4];
	
	static Context context;
	
	static boolean isInitialized=false;
	
	public static void setContext(Context cont){
		context = cont;
	}
	
	public static void initializePlayers(){
		if(isInitialized==false){
			players[0] = MediaPlayer.create(context, R.raw.kalimba);
			players[1] = MediaPlayer.create(context, R.raw.click);
			players[2] = MediaPlayer.create(context, R.raw.gamewin);
			players[3] = MediaPlayer.create(context, R.raw.gamemusic); 
			players[3].setLooping(true);
			isInitialized=true;
			isMuted[0] = false;
			isMuted[3] = false;
		}
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