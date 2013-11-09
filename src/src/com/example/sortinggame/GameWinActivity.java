package com.example.sortinggame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class GameWinActivity extends Activity {
	int gameWinNumber;
	float screenX,screenY;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate(savedInstanceState);
        
		setContentView(R.layout.activity_game_win);
		
		ImageView b = (ImageView) this.findViewById(R.id.ball1);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Hit!", Toast.LENGTH_SHORT).show();
				spinBall();
			}
		});
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenY = metrics.heightPixels;
		screenX = metrics.widthPixels;
        
		Intent intent = getIntent();
		gameWinNumber = intent.getIntExtra(GameActivity.EXTRA_MESSAGE, 0); //Returns 0 if no value passed through
		
		startAnimation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_win, menu);
		return true;
	}
	
	public void startAnimation(){		
		AnimatorSet bouncingBalls = new AnimatorSet();
		ImageView ballView = (ImageView) this.findViewById(R.id.ball1);
		
		int Width = ballView.getDrawable().getIntrinsicWidth();
		int Height= ballView.getDrawable().getIntrinsicHeight();
		
		ObjectAnimator rightX = ObjectAnimator.ofFloat(ballView, "translationX", 0f, screenX-Width);
		ObjectAnimator leftX = ObjectAnimator.ofFloat(ballView, "translationX", screenX-Width, 0f);
		ObjectAnimator downY = ObjectAnimator.ofFloat(ballView, "translationY", 0f, screenY-Height);
		ObjectAnimator upY = ObjectAnimator.ofFloat(ballView, "translationY", screenY-Height, 0f);
		
		bouncingBalls.play(rightX).with(downY);
		bouncingBalls.play(upY).after(downY);
		bouncingBalls.play(leftX).after(rightX);
		
		rightX.setDuration(4000);
		leftX.setDuration(4000);
		downY.setDuration(1000);
		upY.setDuration(1000);
		bouncingBalls.start();
			
	}
	public void spinBall(){
		ImageView ballView = (ImageView) this.findViewById(R.id.ball1);
		ObjectAnimator spinBall = ObjectAnimator.ofFloat(ballView, "rotation", 0f, 360f);
		spinBall.setDuration(500);
		spinBall.start();
	}

}
