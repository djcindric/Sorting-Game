package com.example.sortinggame;

import java.lang.reflect.Field;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class GameActivity extends Activity implements OnTouchListener, OnDragListener {

	SortingDB db;
	private ImageView[] images;
	ImageView img;
	TableRow imagePool;
	String level;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		    		
		//Hide the action bar to increase play area
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		//Intent intent = getIntent(); //Retrieve the intent
		
		//position is the integer value of the icons position in the grid
		//int position = intent.getIntExtra(LevelActivity.Icon_Position, -1);
		
		setContentView(R.layout.activity_game);
		
		db = new SortingDB(this);
		
		Intent i = getIntent();
		level = i.getExtras().getString(LevelActivity.LEVEL_NAME);
        images = new ImageView[24]; 
		loadCategoryBackground(level);
		loadImages();
		
		//Allow drag and drop of images to categories
		findViewById(R.id.category1).setOnDragListener(this);
		findViewById(R.id.category2).setOnDragListener(this);
		findViewById(R.id.category3).setOnDragListener(this);
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
	public boolean onDrag(View v, DragEvent dragEvent) {
		int dragAction = dragEvent.getAction();
		View view = (View) dragEvent.getLocalState();
		ViewGroup from = (ViewGroup) view.getParent();
		
		if (dragEvent.getAction() == DragEvent.ACTION_DRAG_STARTED)
			;
		// do nothing
		else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENTERED)
			;
		// do nothing
		else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_EXITED)
			;
		// do nothing
		else if (dragEvent.getAction() == DragEvent.ACTION_DROP) {
			if (checkForValidMove()){
				from.removeView(view);
				LinearLayout to = (LinearLayout) v;
				to.addView(view);
				view.setVisibility(View.VISIBLE);
			}
			else{
			}
				
		}
		else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED);
			//view.setVisibility(View.VISIBLE);

		return true;
	}
	
	public boolean checkForValidMove(){
		return true;
	}
	
	private void loadImages() {
    	Cursor test = db.query("SELECT path FROM  Images", null);
    	Class drawable = R.drawable.class;
    	Class ids = R.id.class;
		Field field;
		int identifier;
		for(int i = 0; i < images.length; i++) {
			try {
				test.moveToNext();
				int x = i + 1;
				field = ids.getField("imagePool" + x);
				identifier = field.getInt(null);
				images[i] = (ImageView) findViewById(identifier);
				field = drawable.getField(test.getString(test.getColumnIndex("path")));
				identifier = field.getInt(null);
				images[i].setImageResource(identifier);
				images[i].setOnTouchListener(this);
				/*
				if(i % 3 == 0)
					imagePool = (TableRow) findViewById(R.id.imagePoolRow1);
				else if(i % 3 == 1)
					imagePool = (TableRow) findViewById(R.id.imagePoolRow1);
				else
					imagePool = (TableRow) findViewById(R.id.imagePoolRow1);
				
					imagePool.addView(images[i]);
					*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("MyTag", "Failure to get drawable id. Path = " + test.getString(test.getColumnIndex("path")), e);
			}
		}
    }
	private void loadCategoryBackground(String level) {
    	Cursor test = db.query("SELECT background FROM Level WHERE name=?", new String[]{level});
    	Class res = R.drawable.class;
		Field field;
		try {
			test.moveToNext();
			field = res.getField(test.getString(test.getColumnIndex("background")));
			int identifier = field.getInt(null);
			
			TableLayout layout = (TableLayout) findViewById(R.id.catergories);
			layout.setBackgroundResource(identifier);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("MyTag", "Failure to get drawable id. Path = " + test.getString(test.getColumnIndex("path")), e);
		}
	}
}
