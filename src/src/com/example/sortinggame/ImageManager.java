package com.example.sortinggame;

import java.lang.ref.SoftReference;
import java.util.Set;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

public class ImageManager {
	private Context mContext;
	private LruCache<String, Bitmap> mMemoryCache;
	Set<SoftReference<Bitmap>> mReusableBitmaps;

	public ImageManager() {
		mContext = null;
		
		// Get max available VM memory, exceeding this amount will throw an
	    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
	    // int in its constructor.
	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	    // Use 1/8th of the available memory for this memory cache.
	    final int cacheSize = maxMemory / 8;

	    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            // The cache size will be measured in kilobytes rather than
	            // number of items.
	            return bitmap.getByteCount() / 1024;
	        }
	    };


	}
	

	public Bitmap decodeSampledBitmapFromFile(String path, int reqWidth,
			int reqHeight) {
		
		 final String imageKey = String.valueOf(path);

		    final Bitmap bitmap = getBitmapFromMemCache(imageKey);
		    if (bitmap != null) {
		        return bitmap;
		    } else {
		    	// First decode with inJustDecodeBounds=true to check dimensions
				final BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(path, options);

				// Calculate inSampleSize
				options.inSampleSize = calculateInSampleSize(options, reqWidth,
						reqHeight);

				// Decode bitmap with inSampleSize set
				options.inJustDecodeBounds = false;
				Bitmap bitmap1 =  BitmapFactory.decodeFile(path, options);
				addBitmapToMemoryCache(String.valueOf(path), bitmap1);
				return bitmap1;
		    }
	}

	public Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {
		
		final String imageKey = String.valueOf(resId);

	    final Bitmap bitmap = getBitmapFromMemCache(imageKey);
	    if (bitmap != null) {
	        return bitmap;
	    } else {
	    	// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(res, resId, options);

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			Bitmap bitmap1 =  BitmapFactory.decodeResource(res, resId, options);
			addBitmapToMemoryCache(String.valueOf(resId), bitmap1);
			return bitmap1;
	    }
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	        mMemoryCache.put(key, bitmap);
	    }
	}

	public Bitmap getBitmapFromMemCache(String key) {
	    return mMemoryCache.get(key);
	}

}
