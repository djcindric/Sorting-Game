package com.example.sortinggame;

public class Category
{
	private String name, level;
	private int position;
	
	public Category(String catName, String levelName)
	{
		this.name = catName;
		this.level = levelName;
	}
	public Category() {
		this.name = null;
		this.level = null;
	}
	public void setName(String catName)
	{
		this.name = catName;
	}
	public String getName()
	{
		return name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
}
