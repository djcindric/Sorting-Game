package com.example.sortinggame;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class GameActivity extends Activity implements OnTouchListener,
		OnDragListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Hide the action bar to increase play area
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		Intent intent = getIntent(); //Retrieve the intent
		
		//Retrieve the icon's position in the grid from the intent. 
		//position is the integer value of the icons position in the grid
		int position = intent.getIntExtra(LevelActivity.Icon_Position, -1);
		
		//Display Position for reference
		Toast.makeText(GameActivity.this, "" + position, Toast.LENGTH_LONG).show(); 
		
		setContentView(R.layout.activity_game);
		findViewById(R.id.image1).setOnTouchListener(this);
		findViewById(R.id.top).setOnDragListener(this);
		findViewById(R.id.image2).setOnTouchListener(this);
		findViewById(R.id.bottom).setOnDragListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
			v.startDrag(null, shadowBuilder, v, 0);
			v.setVisibility(View.INVISIBLE);
			return true;
		} else
			return false;
	}

	@Override
	public boolean onDrag(View v, DragEvent e) {
		int action = e.getAction();
		View view = (View) e.getLocalState();
		ViewGroup from = (ViewGroup) view.getParent();
		if (e.getAction() == DragEvent.ACTION_DRAG_STARTED)
			;
		// do nothing
		else if (e.getAction() == DragEvent.ACTION_DRAG_ENTERED)
			;
		// do nothing
		else if (e.getAction() == DragEvent.ACTION_DRAG_EXITED)
			;
		// do nothing
		else if (e.getAction() == DragEvent.ACTION_DROP) {
			// Dropped, reassign View to ViewGroup
			from.removeView(view);
			LinearLayout to = (LinearLayout) v;
			to.addView(view);
			view.setVisibility(View.VISIBLE);
		}
		else if (e.getAction() == DragEvent.ACTION_DRAG_ENDED);
			//view.setVisibility(View.VISIBLE);

		return true;
	}

}
