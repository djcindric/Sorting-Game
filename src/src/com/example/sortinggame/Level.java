package com.example.sortinggame;

public class Level
{
	private String name,icon,background;
	private int isPreLoaded, completed, iconPreloaded, backgroundPreloaded;
	
	public Level(String lName, String iName, String bName, int pload, int completed)
	{
		this.name = lName;
		this.icon = iName;
		this.background = bName;
		this.isPreLoaded = pload;
		this.completed = completed;
	}
	public Level()
	{
		name = null;
		icon = null;
		background = null;
		isPreLoaded = -1;
		completed = -1;
		setIconPreloaded(-1);
		setBackgroundPreloaded(-1);
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
	public int isPreLoaded() {
		return isPreLoaded;
	}
	public void setPreLoaded(int isPreLoaded) {
		this.isPreLoaded = isPreLoaded;
	}
	/**
	 * @return the completed
	 */
	public int isCompleted() {
		return completed;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	/**
	 * @return the iconPreloaded
	 */
	public int getIconPreloaded() {
		return iconPreloaded;
	}
	/**
	 * @param iconPreloaded the iconPreloaded to set
	 */
	public void setIconPreloaded(int iconPreloaded) {
		this.iconPreloaded = iconPreloaded;
	}
	/**
	 * @return the backgroundPreloaded
	 */
	public int getBackgroundPreloaded() {
		return backgroundPreloaded;
	}
	/**
	 * @param backgroundPreloaded the backgroundPreloaded to set
	 */
	public void setBackgroundPreloaded(int backgroundPreloaded) {
		this.backgroundPreloaded = backgroundPreloaded;
	}
	
}
