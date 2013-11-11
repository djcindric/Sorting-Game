package com.example.sortinggame;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnTouchListener,
		OnDragListener {

	SortingDB db;
	private ImageView[] images;
	// private ImageView[] sortedImages;
	private ArrayList<Integer> imagePath;
	TableRow imagePool;
	String level;
	GameControl game;
	private boolean initializeImages;
	
	public final static String EXTRA_MESSAGE = "com.example.sortinggame.MESSAGE";

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
		
		TableRow category1 = (TableRow)(findViewById(R.id.category1));
		TableRow category2 = (TableRow)(findViewById(R.id.category2));
		TableRow category3 = (TableRow)(findViewById(R.id.category3));
		
		//sets tags for each category
		category1.setTag(game.getCategory(0).getName());
		category2.setTag(game.getCategory(1).getName());
		category3.setTag(game.getCategory(2).getName());

		initializeImages = true;
		if(!SoundManager.players[3].isPlaying()){
			SoundManager.players[3].seekTo(0);
			SoundManager.playMusic(3);
		}
		
	}
	
	//loads images after onCreate is done
	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		if(initializeImages) {
			loadCategoryBackground(level);
			loadImages();
	
			// Allow drag and drop of images to categories
			TableRow category1 = (TableRow)(findViewById(R.id.category1));
			TableRow category2 = (TableRow)(findViewById(R.id.category2));
			TableRow category3 = (TableRow)(findViewById(R.id.category3));
			category1.setOnDragListener(this);
			category2.setOnDragListener(this);
			category3.setOnDragListener(this);
			
			//loads category symbols
			ImageView symbol1 = (ImageView)(findViewById(R.id.categoryImage1));
			ImageView symbol2 = (ImageView)(findViewById(R.id.categoryImage10));
			ImageView symbol3 = (ImageView)(findViewById(R.id.categoryImage19));
			
			Bitmap bmap1 = getBitmap(game.getCategorySymbols(0).getPath(), game.getCategorySymbols(0).isPreloaded(), symbol1);
			Bitmap bmap2 = getBitmap(game.getCategorySymbols(1).getPath(), game.getCategorySymbols(1).isPreloaded(), symbol2);
			Bitmap bmap3 = getBitmap(game.getCategorySymbols(2).getPath(), game.getCategorySymbols(2).isPreloaded(), symbol3);
			
			symbol1.setImageBitmap(bmap1);
			symbol2.setImageBitmap(bmap2);
			symbol3.setImageBitmap(bmap3);
			
			initializeImages = false;
		}
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
			if(game.checkForValidMove((String) v.getTag(), (String) view.getTag())) {
				TableRow category = (TableRow) v;
				
				//gets the next column to put the image in
				int row = 0;
				String imageCategory = (String) view.getTag();
				
				//updates number of sorted images
				game.update(imageCategory);
				
				if(imageCategory.equals(game.getCategory(0).getName()))
					row = game.getCategoryOneSorted();
				else if(imageCategory.equals(game.getCategory(1).getName()))
					row = game.getCategoryTwoSorted();
				else
					row = game.getCategoryThreeSorted();
					
				//places image in correct row
				ImageView img = (ImageView) category.getChildAt(row);
				Drawable copyImg = view.getDrawable();
				img.setImageDrawable(copyImg);
				img.setVisibility(View.VISIBLE);
				
				if(game.getImagesSorted() < 17) {
					view.setImageBitmap(getBitmap(game.getNextImage().getPath(), game.getNextImage().isPreloaded(), view));
					view.setTag(game.getNextImage().getCatName());
					view.setVisibility(View.VISIBLE);
				}
				
				if(game.checkForWin()) {
					gameWin();
				}
			}
			else
				view.setVisibility(View.VISIBLE);
		} else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
			//snaps image back to item pool
			if (!dragEvent.getResult())
				view.setVisibility(View.VISIBLE);
		}
		return true;
	}

	private void loadImages() {
		Class res = R.id.class;
		Field field;
		int identifier;
		Bitmap bmap = null;
		for (int i = 0; i < images.length; i++) {
			int x = i + 1;
			try {
				field = res.getField("imagePool" + x);				
				identifier = field.getInt(null);
				images[i] = (ImageView)(findViewById(identifier));
				bmap = getBitmap(game.getImages(i).getPath(), game.getImages(i).isPreloaded(), images[i]);
				images[i].setImageBitmap(bmap);
				images[i].setTag(game.getImages(i).getCatName());
				images[i].setOnTouchListener(this);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("MyTag","", e);
			}
		}
	}

	private void loadCategoryBackground(String level) {
		Cursor test = db.query("SELECT background FROM Level WHERE name=?", new String[] { level });
		Class res = R.drawable.class;
		Field field;
		try {
			test.moveToNext();
			field = res.getField(test.getString(test.getColumnIndex("background")));
			int identifier = field.getInt(null);

			TableLayout layout = (TableLayout) findViewById(R.id.categories);
			layout.setBackgroundResource(identifier);
			System.out.print("Background width - " + layout.getWidth() + "   height " + layout.getHeight());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("MyTag",
					"Failure to get drawable id. Path = " + test.getString(test.getColumnIndex("path")), e);
		}
	}
	
	private Bitmap getBitmap (String path, int isPreloaded, ImageView img) {
		Bitmap bmap = null;
		int height =  img.getHeight();
		int width = img.getWidth();
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		
		if(isPreloaded == 0) {
			// First decode with inJustDecodeBounds=true to check dimensions
			options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(path, options);

		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, width, height);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
			return  BitmapFactory.decodeFile(path, options);
		} else {
			Class res = R.drawable.class;
			Field field;
			int identifier;
			try {
				//Find resource id
				field = res.getField(path);
				identifier = field.getInt(null);
				
				// First decode with inJustDecodeBounds=true to check dimensions
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeResource(getResources(), identifier, options);

			    // Calculate inSampleSize
			    options.inSampleSize = calculateInSampleSize(options, width, height);

			    // Decode bitmap with inSampleSize set
			    options.inJustDecodeBounds = false;			    		
			    return BitmapFactory.decodeResource(getResources(), identifier, options);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("MyTag", "Failure to get drawable id. Path = " + path, e);
			}
			return bmap;
		}
	}
	
	//scales image down if it needs to
	public static int calculateInSampleSize(
	    BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}
	
	public void gameWin(){
		SoundManager.setContext(this);
		//Stop Game Music
		if(SoundManager.players[3].isPlaying()){
			SoundManager.playMusic(3);
		}
		//Start Game win sound
		SoundManager.playMusic(2);
		
		//Grab the entire game view and clear it
		LinearLayout linear = (LinearLayout) findViewById(R.id.gameScreen);
		linear.removeAllViews();
		
		//Create text to display win message
		TextView winMessage = new TextView(this);
		
		//Create layout for bottom half of screen (buttons)
		LinearLayout bottomHalf = new LinearLayout(this);
		bottomHalf.setGravity(Gravity.CENTER);
		//Parameters for text view
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
		//params1.weight = 1.0f;
		
		//Parameters for bottom linear layout
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
		//params1.weight = 1.0f;
		
		//Set the parameters
		bottomHalf.setLayoutParams(params2);
		winMessage.setLayoutParams(params1);
		
		//Initialize text view
		winMessage.setTextSize(40);
		winMessage.setTextColor(Color.WHITE);
		winMessage.setText("You Win!");
		winMessage.setGravity(Gravity.CENTER);
		
		//Create buttons
		Button quitButton = new Button(this);
		Button playAgain = new Button(this);
		Button pickNew = new Button(this);
		
		//Set On clicks for buttons
		playAgain.setOnClickListener(myHandler1);
		playAgain.setText("Play Again");
		pickNew.setOnClickListener(myHandler2);
		pickNew.setText("Play Another Level");
		quitButton.setOnClickListener(myHandler3);
		quitButton.setText("Quit");
		
		
		//Add buttons to bottom view
		bottomHalf.addView(playAgain);
		bottomHalf.addView(pickNew);
		bottomHalf.addView(quitButton);
		
		linear.setBackgroundResource(R.drawable.winbackground);
		linear.addView(winMessage);
		linear.addView(bottomHalf);
		}
	
	//Choose new level
	View.OnClickListener myHandler1 = new View.OnClickListener(){
		public void onClick(View v) {
			Toast toast = Toast.makeText(getBaseContext(), "Play Again", Toast.LENGTH_SHORT);
			toast.show();
		}
	};
	//Quit Game
	View.OnClickListener myHandler2 = new View.OnClickListener(){
		public void onClick(View v) {
			Intent intent = new Intent(getBaseContext(), LevelActivity.class);
			startActivity(intent);
		}
	};
	//Play Again
	View.OnClickListener myHandler3 = new View.OnClickListener(){
		public void onClick(View v) {
			Toast toast = Toast.makeText(getBaseContext(), "Quit Game", Toast.LENGTH_SHORT);
			toast.show();
		}
	};
}
