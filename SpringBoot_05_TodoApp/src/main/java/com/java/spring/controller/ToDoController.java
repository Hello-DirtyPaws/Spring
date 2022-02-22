package com.java.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.spring.model.ToDoItem;
import com.java.spring.service.ToDoService;


@RestController
public class ToDoController
{
	@Autowired
	private ToDoService toDoService;
	
	@GetMapping("/getAllItems")
	public List<ToDoItem> getAllItems()
	{
		return this.toDoService.getAllItems();
	}
	
	@GetMapping("/getItem")
	public ToDoItem getItemById(@RequestParam int id)
	{
		if(id < 1) 
		{
			return null;
		}
		
		return this.toDoService.getItemById(id);
	}
	
	@GetMapping("/getItemsByName")
	public List<ToDoItem> getItemsByName(@RequestParam String name)
	{
		if(name == null || name.trim().length() <= 1)
		{
			return null;
		}
			
		return this.toDoService.getItemsByName(name);
	}
	
	@PostMapping("/addItem")
	public boolean addItem(@RequestParam String name)
	{	
		return this.toDoService.addItem(new ToDoItem(0, name));
	}
	
	@PostMapping("/updateItem")
	public boolean updateItem(@RequestBody ToDoItem item)
	{
		return this.toDoService.updateItem(item);
	}
	
	@DeleteMapping("/deleteItem")
	public boolean deleteItem(@RequestParam int id)
	{
		return this.toDoService.deleteItem(id);
	}
}
