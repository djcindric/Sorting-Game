package com.example.sortinggame;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager extends Activity {
	private static MediaPlayer backGroundPlayer;
	private static Context context;
	
	private static boolean isStarted=false;
	
	private static boolean isMuted = false;
	
	private static boolean permMute = false;
	
	public static void setPermMute(boolean b){
		if (backGroundPlayer.isPlaying()){
			setMuted(true);
		}
		permMute=b;
	}
	public static void setContext(Context cont){
		context = cont;
	}
	public static void setMusic(int resource){
		backGroundPlayer = MediaPlayer.create(context, resource);
		backGroundPlayer.setLooping(true);
		backGroundPlayer.setVolume(100, 100);
	}
	
	public static void changeMusic(int resource){
		backGroundPlayer.reset();
		backGroundPlayer = MediaPlayer.create(context, resource);
		backGroundPlayer.setLooping(true);
		backGroundPlayer.setVolume(100, 100);
	}
	public static boolean playMusic(){
		if (isStarted==false){
		backGroundPlayer.start();
		isStarted=true;
		return true;}
		return false;
	}
	
	public static void setMuted(boolean muted){
		if(backGroundPlayer.isPlaying()){
			backGroundPlayer.pause();
			isMuted=true;
		}
		else{
			backGroundPlayer.start();
			isMuted = false;
		}
	}
	public static String checkSoundState(){
		if (isMuted){
			return "off";
		}
		return "on";
	}
	public static void quit(){
		if(backGroundPlayer != null){
			backGroundPlayer.release();
		}
	}
}
