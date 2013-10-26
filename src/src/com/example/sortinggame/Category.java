package com.example.sortinggame;

public class Category
{
	public String name, level;
	
	public Category(String catName, Level levelName)
	{
		this.name = catName;
		this.level = levelName.name;
	}
	public void setName(String catName)
	{
		this.name = catName;
	}
	public String getName()
	{
		return name;
	}
}
