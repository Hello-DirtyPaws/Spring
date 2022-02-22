package com.java.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.spring.model.ToDoItem;
import com.java.spring.repository.ToDoItemRepo;

@Service
public class ToDoService
{
	@Autowired
	private ToDoItemRepo toDoRepo;
	
	public List<ToDoItem> getAllItems()
	{
		return this.toDoRepo.findAll();
	}
	
	public ToDoItem getItemById(int id)
	{
		return this.toDoRepo.findById(id).orElse(null);
	}
	
	public List<ToDoItem> getItemsByName(String name)
	{
		return this.toDoRepo.findItemsByName(name);
	}
	
	public boolean addItem(ToDoItem item)
	{
		if(this.toDoRepo.findById(item.getId()).isEmpty())
		{
			this.toDoRepo.save(item);
			return true;
		}
		return false;
	}
	
	public boolean updateItem(ToDoItem item)
	{
		Optional<ToDoItem> oldItem = this.toDoRepo.findById(item.getId());
		if(oldItem.isPresent())
		{
			oldItem.get().setName(item.getName());
			this.toDoRepo.save(oldItem.get());
			return true;
		}
		return false;
	}
	
	public boolean deleteItem(int itemId)
	{
		Optional<ToDoItem> oldItem = this.toDoRepo.findById(itemId);
		if(oldItem.isPresent())
		{
			this.toDoRepo.delete(oldItem.get());
			return true;
		}
		return false;
	}
}
