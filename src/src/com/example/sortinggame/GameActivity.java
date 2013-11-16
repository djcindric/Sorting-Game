package com.example.sortinggame;
import java.lang.reflect.Field;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class GameActivity extends Activity implements OnTouchListener, OnDragListener {

	SortingDB db;
	private ImageView[] images;
	TableRow imagePool;
	String level;
	GameControl game;	
	private ImageView[] categorySymbols;
	ImageLoader imageLoader;

	public final static String EXTRA_MESSAGE = "com.example.sortinggame.MESSAGE";
	public final static String LEVEL_NAME_REPLAY = "com.example.sortinggame.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// Hide the action bar to increase play area
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		setContentView(R.layout.activity_game);

		Intent intent = getIntent();
		level = intent.getExtras().getString(LevelActivity.LEVEL_NAME);	
		game = new GameControl(this, level);
		db = new SortingDB(this);

		images = new ImageView[8];
		initImageLoader();
		
		SoundManager.players[3].seekTo(0);
		
		TableRow category1 = (TableRow)(findViewById(R.id.category1));
		TableRow category2 = (TableRow)(findViewById(R.id.category2));
		TableRow category3 = (TableRow)(findViewById(R.id.category3));
		
		intializeLevelBackground();
		
		//sets tags for each category
		category1.setTag(game.getCategory(0).getName());
		category2.setTag(game.getCategory(1).getName());
		category3.setTag(game.getCategory(2).getName());
	
		// Allow drag and drop of images to categories
		category1.setOnDragListener(this);
		category2.setOnDragListener(this);
		category3.setOnDragListener(this);
		
		//loads image pool
		initializeImagePool();
				
		//loads category symbols
		categorySymbols = new ImageView[3];
		
		categorySymbols[0] = (ImageView)(findViewById(R.id.categoryImage1));
		categorySymbols[1] = (ImageView)(findViewById(R.id.categoryImage10));
		categorySymbols[2] = (ImageView)(findViewById(R.id.categoryImage19));
		
		for(int i = 0; i < game.getNumOfCategories(); i++) 
			loadImage(game.getCategorySymbols(i).getPath(), game.getCategorySymbols(i).isPreloaded(), categorySymbols[i]);
	}
	
	protected void onStart(){
    	super.onStart();
    	
    	//Start menu music
    	if(SoundManager.isMuted[3] == false){
    		SoundManager.playMusic(3);
    	}
    	
    }

    protected void onPause(){
    	super.onPause();
    	//Pause music when app is out of focus
    	if(SoundManager.players[3].isPlaying()){
    		SoundManager.playMusic(3);
    		SoundManager.isMuted[3] = false;
    	}
    }
	
	private void initImageLoader() {
        DisplayImageOptions defaultOptions =  new DisplayImageOptions.Builder()
        	.showStubImage(R.drawable.ic_stub)
            .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
        	this).defaultDisplayImageOptions(defaultOptions).memoryCache(
        	new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	//Handle presses on action bar
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.sound:
        	disableSound();
        	return true;
        default:
                return super.onOptionsItemSelected(item);
        }
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
		ImageView view = (ImageView) dragEvent.getLocalState();

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
			if(game.checkForValidMove((Integer) v.getTag(), (Integer) view.getTag())) {
				TableRow category = (TableRow) v;
				
				//gets the next column to put the image in
				int row = 0;
				int imageCategory = (Integer) view.getTag();
				
				//updates number of sorted images
				game.update(imageCategory);
				
				if(imageCategory == game.getCategory(0).getName())
					row = game.getCategoryOneSorted();
				else if(imageCategory == game.getCategory(1).getName())
					row = game.getCategoryTwoSorted();
				else
					row = game.getCategoryThreeSorted();
					
				//places image in correct row
				ImageView img = (ImageView) category.getChildAt(row);
				Drawable copyImg = view.getDrawable();
				img.setImageDrawable(copyImg);
				img.setVisibility(View.VISIBLE);
				
				//loads next image in item pool
				if(game.getNextImage() != null) {
					loadImage(game.getNextImage().getPath(), game.getNextImage().isPreloaded(), view);
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
			//snaps image back to item pool if placed in wrong category
			if (!dragEvent.getResult())
				view.setVisibility(View.VISIBLE);
		}
		return true;
	}

	//sets picture for imageview; will either use path to sd card or resource id
	private void loadImage(String path, int isPreloaded, ImageView imageView) {
		Class res = R.id.class;
		Field field;
		int identifier;
		
			if(isPreloaded == 0)
				imageLoader.displayImage("file://" + path, imageView);
			else {
				try {
					//Find resource id
					res = R.drawable.class;
					field = res.getField(path);
					identifier = field.getInt(null);
					imageView.setImageResource(identifier);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("MyTag", "Failure to get drawable id. Path = " + path, e);
				}
			}
		}
	
	//Initializes image pool
	private void initializeImagePool() {
		Class res = R.id.class;
		Field field;
		int identifier;
		Bitmap bmap = null;
		for (int i = 0; i < images.length; i++) {
			int x = i + 1;
			try {
				if(i < game.getTotalNumOfImages()) {
					res = R.id.class;
					field = res.getField("imagePool" + x);				
					identifier = field.getInt(null);
					images[i] = (ImageView)(findViewById(identifier));
					String path = game.getImages(i).getPath();
					int isPreloaded = game.getImages(i).isPreloaded();
					loadImage(path, isPreloaded, images[i]);
					images[i].setTag(game.getImages(i).getCatName());
					images[i].setOnTouchListener(this);
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("MyTag","", e);
			}
		}
	}
	
	
	//Initializes level background
	private void intializeLevelBackground() {
		Class res = R.drawable.class;
		Field field;
		String background = game.getLevel().getBackground();
		try {
			field = res.getField(background);
			int identifier = field.getInt(null);

			TableLayout layout = (TableLayout) findViewById(R.id.categories);
			layout.setBackgroundResource(identifier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("MyTag",
					"Failure to get drawable id. Path = " + background, e);
		}
		
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
		quitButton.setText("Main Menu");
		
		
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
			Intent intent = new Intent(getBaseContext(), GameActivity.class);
			intent.putExtra(LEVEL_NAME_REPLAY, level);
			startActivity(intent);
			finish();
		}
	};
	//Quit Game
	View.OnClickListener myHandler2 = new View.OnClickListener(){
		public void onClick(View v) {
			finish();
		}
	};
	//Play Again
	View.OnClickListener myHandler3 = new View.OnClickListener(){
		public void onClick(View v) {
			Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
			startActivity(intent);
		}
	};
	public void disableSound(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Sound is currently " + SoundManager.checkSoundState(0) +
    				". Would you like to change it?").setTitle("Change Sound State");
    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			SoundManager.playMusic(3);
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
}
