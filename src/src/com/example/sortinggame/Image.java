package com.example.sortinggame;

public class Image 
{
	private String imgpath, view, cat;
	private int isPreloaded;
	
	public Image(String path, String catName, int preloaded)
	{
		this.imgpath = path;
		this.cat = catName;
		this.isPreloaded = preloaded;
	}
	public Image(String path, String catName, String view)
	{
		this.imgpath = path;
		this.cat = catName;
		this.view = view;
	}
	public Image() {
		this.imgpath = null;
		this.cat = null;
		this.view = null;
		this.isPreloaded = 0;
	}
	public void setPath(String path)
	{
		this.imgpath = path;
	}
	public String getPath()
	{
		return imgpath;
	}
	public void setCatName(String catName)
	{
		this.cat = catName;
	}
	public String getCatName()
	{
		return cat;
	}
	
	public void setView(String imgview)
	{
		this.view = imgview;
	}
	public String getView()
	{
		return view;
	}
	public int isPreloaded() {
		return isPreloaded;
	}
	public void setPreloaded(int isPreloaded) {
		this.isPreloaded = isPreloaded;
	}
	
}
