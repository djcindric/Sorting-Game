package com.example.sortinggame;

public class Level
{
	public String name,icon,background;
	public boolean isPreLoaded;
	
	public Level(String lName, String iName, String bName, boolean pload)
	{
		this.name = lName;
		this.icon = iName;
		this.background = bName;
		this.isPreLoaded = pload;
	}
	public Level()
	{
		this.name = null;
		this.icon = null;
		this.background = null;
		this.isPreLoaded = false;
	}
	public void setName(String lName)
	{
		this.name = lName;
	}
	public String getName()
	{
		return name;
	}
	public void setIcon(String iName)
	{
		this.icon = iName;
	}
	public String getIcon()
	{
		return icon;
	}
}
