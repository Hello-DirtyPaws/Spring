package com.java.spring.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.spring.model.ToDoItem;
import com.java.spring.repository.ToDoItemRepo;

@Service
public class ToDoService
{
	private static final Logger log = LoggerFactory.getLogger(ToDoService.class);
	
	@Autowired
	private ToDoItemRepo toDoRepo;
	
	public List<ToDoItem> getAllItems()
	{
		log.debug("Calling To Do Item Repository's findAll()");
		return this.toDoRepo.findAll();
	}
	
	public ToDoItem getItemById(int id)
	{
		log.debug("Calling To Do Item Repository's findById(id).orElse(null). ID : [{}]", id);
		return this.toDoRepo.findById(id).orElse(null);
	}
	
	public List<ToDoItem> getItemsByName(String name)
	{
		log.debug("Calling To Do Item Repository's findItemsByName(). Name : [{}]", name);
		return this.toDoRepo.findItemsByName(name);
	}
	
	public int addItem(ToDoItem item)
	{
		log.debug("Calling To Do Item Repository's findById() from passed Item : [{}]", item);
		if(this.toDoRepo.findById(item.getId()).isEmpty())
		{
			log.info("No item exists with ID : [{}]", item.getId());
			ToDoItem newItem = this.toDoRepo.save(item);
			
			log.info("New Item added with a new Auto-Generated ID : [{}]", newItem.getId());
			return newItem.getId();
		}
		
		log.error("Error! Item with ID : [{}] already exists.", item.getId());
		return -1;
	}
	
	public boolean updateItem(ToDoItem item)
	{
		log.debug("Calling To Do Item Repository's findById() from passed Item : [{}]", item);
		Optional<ToDoItem> oldItem = this.toDoRepo.findById(item.getId());
		if(oldItem.isPresent())
		{
			log.debug("Found item to upadte. Old Item : [{}]", oldItem.get());
			oldItem.get().setName(item.getName());
			this.toDoRepo.save(oldItem.get());
			
			log.debug("Successfully updated the item. New Item : [{}]", item);
			return true;
		}
		
		log.error("Error! Unable to update the item. Item does not exists. ID : [{}]", item.getId());
		return false;
	}
	
	public ToDoItem deleteItem(int itemId)
	{
		log.debug("Calling To Do Item Repository's findById(). ID : [{}]", itemId);
		Optional<ToDoItem> oldItem = this.toDoRepo.findById(itemId);
		if(oldItem.isPresent())
		{
			log.debug("Found item to delete. Item : [{}]", oldItem.get());
			this.toDoRepo.delete(oldItem.get());
			
			log.debug("Successfully deleted item. ID : [{}]", itemId);
			return oldItem.get();
		}
		
		log.error("Error! Unable to delete the item. Item does not exists. ID : [{}]", itemId);
		return null;
	}
}
