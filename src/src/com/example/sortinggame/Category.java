package com.example.sortinggame;

public class Category
{
	private String level, path;
	private int name;
	private boolean preloaded;
	
	public Category(int catName, String levelName, String path, boolean preloaded)
	{
		this.name = catName;
		this.level = levelName;
		this.path = path;
		this.setPreloaded(preloaded);
	}
	public Category() {
		this.name = -1;
		this.level = null;
	}
	public void setName(int catName)
	{
		this.name = catName;
	}
	public int getName()
	{
		return name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the preloaded
	 */
	public boolean isPreloaded() {
		return preloaded;
	}
	/**
	 * @param preloaded the preloaded to set
	 */
	public void setPreloaded(boolean preloaded) {
		this.preloaded = preloaded;
	}
}
