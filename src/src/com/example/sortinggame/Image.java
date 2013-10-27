package com.example.sortinggame;

public class Image 
{
public String imgpath, view, cat;
	
	public Image(String path, Category catName)
	{
		this.imgpath = "/drawable/"+ path;
		this.cat = catName.name;
	}
	public Image(String path, Category catName, String view)
	{
		this.imgpath = path;
		this.cat = catName.name;
		this.view = view;
	}
	public void setPath(String path)
	{
		this.imgpath = "/drawable/"+ path;
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
}
