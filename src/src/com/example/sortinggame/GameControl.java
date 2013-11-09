package com.example.sortinggame;

import java.util.ArrayList;
import java.util.Random;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.widget.ImageView;

public class GameControl {
	private SortingDB db;
	private Level level;
	private Category[] categories;
	private Image[] images;
	private Image[] categorySymbols;
	private int imagesSorted;
	private int categoryOneSorted;
	private int categoryTwoSorted;
	private int categoryThreeSorted;
	
	
	public GameControl(Context mContext,  String l)
	{
		db = new SortingDB(mContext);
		level = new Level();
		categories = new Category[3];
		images = new Image[24];
		categorySymbols = new Image[3];
		imagesSorted = 0;
		categoryOneSorted = 0;
		categoryTwoSorted = 0;
		categoryThreeSorted = 0;
		
		loadLevel(l);
		loadCategories();
		loadImages();
	}

	private void loadLevel(String l) {
		Cursor query = db.query("SELECT name, background FROM Level WHERE name=?", new String[]{l});;
		
		while(query.moveToNext()) {
			level.setName(query.getString(query.getColumnIndex("name")));
			level.setBackground(query.getString(query.getColumnIndex("background")));
		}
	}
	
	private void loadCategories() {
		Cursor query = db.query("SELECT categoryName, position FROM Category WHERE levelName=?", new String[]{level.getName()});;
		
		for (int i = 0; i < categories.length; i++) {
			query.moveToNext();
			categories[i] = new Category();
			categories[i].setName(query.getString(query.getColumnIndex("categoryName")));
			categories[i].setPosition(query.getInt(query.getColumnIndex("position")));
		}
	}
	
	private void loadImages() {
		Cursor query = db.query("SELECT path, Images.categoryName, preloaded, Category.position FROM Images, Category WHERE Images.categoryName = Category.categoryName", null);
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		ArrayList<Integer> usedImages = new ArrayList<Integer>();
		boolean addImage = true;
		boolean imageLoaded = false;
		Random rand = new Random();
		
		//randomized images
		for (int i = 0; i < images.length; i++) {
			imageLoaded = false;
			int row;
			while (imageLoaded == false) {
				query.moveToFirst();
				row = rand.nextInt(query.getCount());
				query.move(row);
				
				//checks to see if row has already been used
				for(int x = 0; x < usedImages.size(); x++) {
					if(row != usedImages.get(x))
						addImage = true;
					else {
						addImage = false;
						break;
					}
				}
				
				if(addImage == true) {
					if(query.getInt(3) == 1 && num1 < 8) {			
						images[i] = new Image();
						images[i].setCatName(query.getString(query.getColumnIndex("categoryName")));
						images[i].setPath(query.getString(query.getColumnIndex("path")));
						images[i].setPreloaded(query.getInt(query.getColumnIndex("preloaded")));
						num1++;
						usedImages.add(row);
						imageLoaded = true;
					} else if(query.getInt(3) == 2 && num2 < 8) {
						images[i] = new Image();
						images[i].setCatName(query.getString(query.getColumnIndex("categoryName")));
						images[i].setPath(query.getString(query.getColumnIndex("path")));
						images[i].setPreloaded(query.getInt(query.getColumnIndex("preloaded")));
						num2++;
						usedImages.add(row);
						imageLoaded = true;
					} else if(query.getInt(3) == 3 && num3 < 8) {
						images[i] = new Image();
						images[i].setCatName(query.getString(query.getColumnIndex("categoryName")));
						images[i].setPath(query.getString(query.getColumnIndex("path")));
						images[i].setPreloaded(query.getInt(query.getColumnIndex("preloaded")));
						num3++;
						usedImages.add(row);
						imageLoaded = true;
					} else
						imageLoaded = false;
				}
			}
		}
	
		num1 = 0;
		num2 = 0;
		num3 = 0;
		
		//randomized category symbols
		for (int i = 0; i < categorySymbols.length; i++) {
			imageLoaded = false;
			while (imageLoaded == false) {
				query.moveToFirst();
				int row = rand.nextInt(query.getCount());
				query.move(row);
				
				//checks to see if row has already been used
				for(int x = 0; x < usedImages.size(); x++) {
					if(row != usedImages.get(x))
						addImage = true;
					else {
						addImage = false;
						break;
					}
				}
				
				if(addImage == true) {
					if(query.getInt(3) == 1 && num1 < 1 && i == 0) {			
						categorySymbols[i] = new Image();
						categorySymbols[i].setCatName(query.getString(query.getColumnIndex("categoryName")));
						categorySymbols[i].setPath(query.getString(query.getColumnIndex("path")));
						categorySymbols[i].setPreloaded(query.getInt(query.getColumnIndex("preloaded")));
						num1++;
						usedImages.add(row);
						imageLoaded = true;
					} else if(query.getInt(3) == 2 && num2 < 1 && i == 1) {
						categorySymbols[i] = new Image();
						categorySymbols[i].setCatName(query.getString(query.getColumnIndex("categoryName")));
						categorySymbols[i].setPath(query.getString(query.getColumnIndex("path")));
						categorySymbols[i].setPreloaded(query.getInt(query.getColumnIndex("preloaded")));
						num2++;
						usedImages.add(row);
						imageLoaded = true;
					} else if(query.getInt(3) == 3 && num3 < 1 && i == 2) {
						categorySymbols[i] = new Image();
						categorySymbols[i].setCatName(query.getString(query.getColumnIndex("categoryName")));
						categorySymbols[i].setPath(query.getString(query.getColumnIndex("path")));
						categorySymbols[i].setPreloaded(query.getInt(query.getColumnIndex("preloaded")));
						num3++;
						usedImages.add(row);
						imageLoaded = true;
					} else
						imageLoaded = false;
				}
			}
		}
	}
	
	public boolean checkForValidMove(String dragCategory, String imageCategory) {
		if(dragCategory.equals(imageCategory)) {
			return true;	
		}
		else
			return false;
	}
	
	public void update(String category) {
		imagesSorted++;
		
		if(categories[0].getName().equals(category))
			categoryOneSorted++;
		else if(categories[1].getName().equals(category))
			categoryTwoSorted++;
		else
			categoryThreeSorted++;
	}
	
	public boolean checkForWin() {
		if(imagesSorted == 24) {
			return true;	
		}
		else
			return false;
	}

	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}


	public Category getCategory(int index) {
		return categories[index];
	}


	public void setCategories(Category[] categories) {
		this.categories = categories;
	}

	public Image getImages(int index) {
		return images[index];
	}

	public void setImages(Image[] images) {
		this.images = images;
	}

	public Image getCategorySymbols(int index) {
		return categorySymbols[index];
	}

	public void setCategorySymbols(Image[] categorySymbols) {
		this.categorySymbols = categorySymbols;
	}

	public int getImagesSorted() {
		return imagesSorted;
	}

	public void setImagesSorted(int imagesSorted) {
		this.imagesSorted = imagesSorted;
	}
	
	public Image getNextImage() {
		return images[imagesSorted + 7];
	}

	public int getCategoryOneSorted() {
		return categoryOneSorted;
	}

	public void setCategoryOneSorted(int categoryOneSorted) {
		this.categoryOneSorted = categoryOneSorted;
	}

	public int getCategoryTwoSorted() {
		return categoryTwoSorted;
	}

	public void setCategoryTwoSorted(int categoryTwoSorted) {
		this.categoryTwoSorted = categoryTwoSorted;
	}

	public int getCategoryThreeSorted() {
		return categoryThreeSorted;
	}

	public void setCategoryThreeSorted(int categoryThreeSorted) {
		this.categoryThreeSorted = categoryThreeSorted;
	}
	
}