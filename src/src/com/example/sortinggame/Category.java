package com.example.sortinggame;

public class Category
{
	private String level, path;
	private int name;
	
	public Category(int catName, String levelName, String path)
	{
		this.name = catName;
		this.level = levelName;
		this.setPath(path);
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
}
