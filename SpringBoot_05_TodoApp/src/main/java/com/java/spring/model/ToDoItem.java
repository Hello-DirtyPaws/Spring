package com.java.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Items")
public class ToDoItem
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	
	public ToDoItem() {}

	public ToDoItem(int id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public int hashCode()
	{
		// Hashcode is based on the ID itself
		return ((Integer) this.id).hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj != null && obj instanceof ToDoItem)
		{
			ToDoItem item = (ToDoItem) obj;

			// Return true if the ID and Name both are equal, else false
			return (this.id == item.id && this.name.equals(item.name));
		}
		return false;
	}

	public String toString()
	{
		return "id: " + this.id + ", name: " + this.name;
	}
}
