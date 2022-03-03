package com.java.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.spring.model.ToDoItem;
import com.java.spring.service.ToDoService;


@RestController
public class ToDoController
{
	private static final Logger log = LoggerFactory.getLogger(ToDoController.class);
	
	@Autowired
	private ToDoService toDoService;
	
	@GetMapping("/getAllItems")
	public List<ToDoItem> getAllItems()
	{
		log.info("GET : [/getAllItems] invoked");
		List<ToDoItem> allItems = this.toDoService.getAllItems();
		
		log.info("Returning the list of all items. Total Items [{}]", allItems.size());
		return allItems;
	}
	
	@GetMapping("/getItem/{id}")
	public ToDoItem getItemById(@PathVariable int id)
	{
		log.info("GET : [/getItem] invoked");
		if(id < 1) 
		{
			log.warn("Invalid id passed. ID : {}", id);
			return null;
		}
		
		ToDoItem item = this.toDoService.getItemById(id);
		log.info("Retrieved item : [{}]", item);
		return item;
	}
	
	@GetMapping("/getItemsByName")
	public List<ToDoItem> getItemsByName(@RequestParam String name)
	{
		log.info("GET : [/getItemsByName] invoked");
		if(name == null || name.trim().length() < 1)
		{
			log.error("Invalid Item name passed. Name : [{}]", name);
			return null;
		}
		
		List<ToDoItem> items = this.toDoService.getItemsByName(name);
		
		log.info("Total items returned: [{}]", items.size());
		log.debug("Items returned --> {}", items);
		return items;
	}
	
	@PostMapping("/addItem")
	public boolean addItem(@RequestParam String name)
	{	
		log.info("POST : [/addItem] invoked");
		if(name == null || name.trim().length() < 1)
		{
			log.error("Invalid Item name passed. Name : [{}]", name);
			return false;
		}
		
		int newItemId = this.toDoService.addItem(new ToDoItem(0, name.trim()));
		if(newItemId > 0)
		{
			log.info("Successfully added new item. ID : [{}]", newItemId);
			return true;
		}
		else
		{
			log.error("Failed to add new item. ID : [{}]", newItemId);
			return false;
		}		
	}
	
	@PutMapping("/updateItem")
	public boolean updateItem(@RequestBody ToDoItem item)
	{
		log.info("POST : [/updateItem] invoked");
		if(item == null || item.getName() == null || item.getId() < 1 || item.getName().trim().length() < 1)
		{
			log.error("Invalid Item data provided. Item : {}", item);
			return false;
		}
		
		boolean isUpdated = this.toDoService.updateItem(item);
		if(isUpdated)
		{
			log.info("Successfully updated the item. ID : [{}]", item.getId());
		}
		else
		{
			log.error("Error! Updating the item. ID : [{}]", item.getId());
		}
		
		return isUpdated;
	}
	
	@DeleteMapping("/deleteItem/{id}")
	public boolean deleteItem(@PathVariable int id)
	{
		log.info("DELETE : [/deleteItem] invoked");
		if(id < 1)
		{
			log.error("Invalid item id passed. ID : [{}]", id);
			return false;
		}
		
		ToDoItem deletedItem = this.toDoService.deleteItem(id);
		if(deletedItem != null)
		{
			log.info("Successfully deleted the item. Item : [{}]", deletedItem);
			return true;
		}
		else
		{
			log.error("Error Deleting the item. ID : [{}]", id);
			return false;
		}
	}
}
