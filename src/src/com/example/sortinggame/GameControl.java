package com.example.sortinggame;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.Random;

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
	private int numOfCat1Images;
	private int numOfCat2Images;
	private int numOfCat3Images;
	private int totalNumOfImages;
	
	
	public GameControl(Context mContext,  String l)
	{
		db = new SortingDB(mContext);
		level = new Level();
		imagesSorted = 0;
		categoryOneSorted = 0;
        categoryTwoSorted = 0;
        categoryThreeSorted = 0;
		
		loadLevel(l);
		getImagesCounts();
		categories = new Category[3];
        images = new Image[totalNumOfImages];
        categorySymbols = new Image[3];
		loadCategories();
		loadImages();
		db.close();
	}

	private void loadLevel(String l) {
		Cursor query = db.query("SELECT name, background FROM Level WHERE name=?", new String[]{l});
		
		while(query.moveToNext()) {
			level.setName(query.getString(query.getColumnIndex("name")));
			level.setBackground(query.getString(query.getColumnIndex("background")));
		}
	}
	
	private void loadCategories() {
		Cursor query = db.query("SELECT categoryName FROM Category WHERE levelName=?", new String[]{level.getName()});
		
		for (int i = 0; i < categories.length; i++) {
            query.moveToNext();
            categories[i] = new Category();
            categories[i].setName(query.getInt(0));
		}
	}
	
	private void loadImages() {
		Cursor query = db.query("SELECT path, Category.categoryName, preloaded FROM Images, Category WHERE Images.category_id = Category.id and Category.levelName=?", new String[]{level.getName()});
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		ArrayList<Integer> usedImages = new ArrayList<Integer>();
		boolean addImage = true;
		boolean imageLoaded = false;
		Random rand = new Random();
	
		//randomized images
		for (int i = 0; i < totalNumOfImages; i++) {
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
					if(query.getInt(1) == 1 && num1 < numOfCat1Images) {			
						images[i] = new Image();
                        images[i].setPath(query.getString(0));
                        images[i].setCatName(query.getInt(1));
                        images[i].setPreloaded(query.getInt(2));
						num1++;
						usedImages.add(row);
						imageLoaded = true;
					} else if(query.getInt(1) == 2 && num2 < numOfCat2Images) {
						images[i] = new Image();
                        images[i].setPath(query.getString(0));
                        images[i].setCatName(query.getInt(1));
                        images[i].setPreloaded(query.getInt(2));
						num2++;
						usedImages.add(row);
						imageLoaded = true;
					} else if(query.getInt(1) == 3 && num3 < numOfCat3Images) {
						images[i] = new Image();
                        images[i].setPath(query.getString(0));
                        images[i].setCatName(query.getInt(1));
                        images[i].setPreloaded(query.getInt(2));
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
		for (int i = 0; i < 3; i++) {
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
					if(query.getInt(1) == 1 && num1 < 1 && i == 0) {			
						categorySymbols[i] = new Image();
                        categorySymbols[i].setPath(query.getString(0));
                        categorySymbols[i].setCatName(query.getInt(1));
                        categorySymbols[i].setPreloaded(query.getInt(2));
						num1++;
						usedImages.add(row);
						imageLoaded = true;
					} else if(query.getInt(1) == 2 && num2 < 1 && i == 1) {
						categorySymbols[i] = new Image();
                        categorySymbols[i].setPath(query.getString(0));
                        categorySymbols[i].setCatName(query.getInt(1));
                        categorySymbols[i].setPreloaded(query.getInt(2));
						num2++;
						usedImages.add(row);
						imageLoaded = true;
					} else if(query.getInt(1) == 3 && num3 < 1 && i == 2) {
						categorySymbols[i] = new Image();
                        categorySymbols[i].setPath(query.getString(0));
                        categorySymbols[i].setCatName(query.getInt(1));
                        categorySymbols[i].setPreloaded(query.getInt(2));
						num3++;
						usedImages.add(row);
						imageLoaded = true;
					} else
						imageLoaded = false;
				}
			}
		}
	}
	
	private void getImagesCounts() {
		Cursor query = db.query("SELECT count(path) FROM Images, Category WHERE Images.category_id = Category.id and Category.categoryName = 1 and Category.levelName=?", new String[]{level.getName()});
		query.moveToNext();
		
		if(query.getInt(0) < 8)
			numOfCat1Images = query.getInt(0);
		else
			numOfCat1Images = 8;
		
		query = db.query("SELECT count(path) FROM Images, Category WHERE Images.category_id = Category.id and Category.categoryName = 2 and Category.levelName=?", new String[]{level.getName()});
		query.moveToNext();
		
		if(query.getInt(0) < 8)
			numOfCat2Images = query.getInt(0);
		else
			numOfCat2Images = 8;
				
		query = db.query("SELECT count(path) FROM Images, Category WHERE Images.category_id = Category.id and Category.categoryName = 3 and Category.levelName=?", new String[]{level.getName()});
		query.moveToNext();

		if(query.getInt(0) < 8)
			numOfCat3Images = query.getInt(0);
		else
			numOfCat3Images = 8;
		
		if(numOfCat1Images + numOfCat2Images + numOfCat3Images < 24)
			totalNumOfImages = numOfCat1Images + numOfCat2Images + numOfCat3Images;
		else
			totalNumOfImages = 24;
		
	}
	
	public boolean checkForValidMove(Integer dragCategory, Integer imageCategory) {
		if(dragCategory == imageCategory) {
			return true;	
		}
		else
			return false;
	}
	
	public void update(int category) {
		imagesSorted++;
        
        if(categories[0].getName() == category)
                categoryOneSorted++;
        else if(categories[1].getName() == category)
                categoryTwoSorted++;
        else
                categoryThreeSorted++;
	}
	
	public boolean checkForWin() {
		if(imagesSorted == totalNumOfImages) {
			return true;	
		}
		else
			return false;
	}

	public Level getLevel() {
		return level;
	}

	public Category getCategory(int index) {
		return categories[index];
	}

	public Image getImages(int index) {
		return images[index];
	}

	public Image getCategorySymbols(int index) {
		return categorySymbols[index];
	}


	public int getImagesSorted() {
		return imagesSorted;
	}

	public void setImagesSorted(int imagesSorted) {
		this.imagesSorted = imagesSorted;
	}
	
	public Image getNextImage() {
		if(imagesSorted < totalNumOfImages) {
			int index = imagesSorted + 7;
			return images[index];
		}
		else
			return null;
	}

	public int getCategoryOneSorted() {
		return categoryOneSorted;
	}


	public int getCategoryTwoSorted() {
		return categoryTwoSorted;
	}


	public int getCategoryThreeSorted() {
		return categoryThreeSorted;
	}

}