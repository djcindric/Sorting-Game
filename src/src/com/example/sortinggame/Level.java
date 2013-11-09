package com.example.sortinggame;

public class Level
{
	private String name,icon,background;
	private boolean isPreLoaded;
	
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
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public boolean isPreLoaded() {
		return isPreLoaded;
	}
	public void setPreLoaded(boolean isPreLoaded) {
		this.isPreLoaded = isPreLoaded;
	}
	
}
