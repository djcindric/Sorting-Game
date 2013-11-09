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
	static int numPlayers = 3;
	static MediaPlayer[] players = new MediaPlayer[3];
	
	static Context context;
	
	static boolean isInitialized=false;
	static boolean isMuted = false;
	
	public static void setContext(Context cont){
		context = cont;
	}
	
	public static void initializePlayers(){
		if(isInitialized==false){
			players[0] = MediaPlayer.create(context, R.raw.kalimba);
			players[1] = MediaPlayer.create(context, R.raw.click);
			players[2] = MediaPlayer.create(context, R.raw.gamewin);
			isInitialized=true;
		}
	}
	
	public static void playMusic(int playerNumber){
		if(players[playerNumber].isPlaying()){
			players[playerNumber].pause();
			isMuted=true;
		}
		else{
			players[playerNumber].start();
			isMuted=false;
		}
	}
	public static void stopPlayer(int playerNumber){
		players[playerNumber].stop();
	}
	
	public static String checkSoundState(){
		if (isMuted){
			return "off";
		}
		return "on";
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