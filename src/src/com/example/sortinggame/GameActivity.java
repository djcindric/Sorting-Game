package com.example.sortinggame;

import java.lang.reflect.Field;
import java.util.ArrayList;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
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
import android.widget.TableRow;

public class GameActivity extends Activity implements OnTouchListener,
		OnDragListener {

	SortingDB db;
	private ImageView[] images;
	// private ImageView[] sortedImages;
	private ArrayList<Integer> imagePath;
	TableRow imagePool;
	String level;
	GameControl game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// Hide the action bar to increase play area
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		// Intent intent = getIntent(); //Retrieve the intent

		// position is the integer value of the icons position in the grid
		// int position = intent.getIntExtra(LevelActivity.Icon_Position, -1);

		setContentView(R.layout.activity_game);

		Intent i = getIntent();
		level = i.getExtras().getString(LevelActivity.LEVEL_NAME);

		game = new GameControl(this, level);
		db = new SortingDB(this);

		images = new ImageView[8];
		// sortedImages = new ImageView[24];
		imagePath = new ArrayList<Integer>();
		loadCategoryBackground(level);
		loadImages();

		// Allow drag and drop of images to categories
		(findViewById(R.id.category1)).setOnDragListener(this);
		(findViewById(R.id.category2)).setOnDragListener(this);
		(findViewById(R.id.category3)).setOnDragListener(this);
		
		//loads category symbols
		ImageView symbol1 = (ImageView)(findViewById(R.id.categoryImage1));
		ImageView symbol2 = (ImageView)(findViewById(R.id.categoryImage2));
		ImageView symbol3 = (ImageView)(findViewById(R.id.categoryImage3));
		
		if(game.getCategorySymbols(0).isPreloaded() == 1)
			symbol1.setImageResource(getImageResource(game.getCategorySymbols(0).getPath()));
		if(game.getCategorySymbols(1).isPreloaded() == 1)
			symbol2.setImageResource(getImageResource(game.getCategorySymbols(1).getPath()));
		if(game.getCategorySymbols(2).isPreloaded() == 1)
			symbol3.setImageResource(getImageResource(game.getCategorySymbols(2).getPath()));


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
		ImageView view = (ImageView) dragEvent.getLocalState();
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
			LinearLayout category = (LinearLayout) v;
			ImageView img = new ImageView(this);
			img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			Drawable copyImg = view.getDrawable();
			img.setImageDrawable(copyImg);
			category.addView(img);
			view.setImageResource(R.drawable.albertosaurus);
			view.setVisibility(View.VISIBLE);
			/*
			 * if (checkForValidMove()){ from.removeView(view); LinearLayout to
			 * = (LinearLayout) v; to.addView(view);
			 * view.setVisibility(View.VISIBLE); } else{ }
			 */
		} else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
			//snaps image back to item pool
			if (!dragEvent.getResult())
				view.setVisibility(View.VISIBLE);
		}
		return true;
	}

	public boolean checkForValidMove() {
		return true;
	}

	private void loadImages() {
		Class res = R.id.class;
		Field field;
		int identifier;
		for (int i = 0; i < images.length; i++) {
			int x = i + 1;
			try {
				field = res.getField("imagePool" + x);				
				identifier = field.getInt(null);
				images[i] = (ImageView)(findViewById(identifier));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(game.getImages(i).isPreloaded() == 1) {
				identifier = getImageResource(game.getImages(i).getPath());
				images[i].setImageResource(identifier);
				images[i].setOnTouchListener(this);
			}
		}
	}

	private void loadCategoryBackground(String level) {
		Cursor test = db.query("SELECT background FROM Level WHERE name=?",
				new String[] { level });
		Class res = R.drawable.class;
		Field field;
		try {
			test.moveToNext();
			field = res.getField(test.getString(test.getColumnIndex("background")));
			int identifier = field.getInt(null);

			LinearLayout layout = (LinearLayout) findViewById(R.id.categories);
			layout.setBackgroundResource(identifier);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("MyTag",
					"Failure to get drawable id. Path = "
							+ test.getString(test.getColumnIndex("path")), e);
		}
	}
	
	private int getImageResource(String path) {
		Class res = R.drawable.class;
		Field field;
		int identifier;
		try {
			field = res.getField(path);
			return identifier = field.getInt(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("MyTag", "Failure to get drawable id. Path = " + path, e);
			return 0;
		}
	}
}
