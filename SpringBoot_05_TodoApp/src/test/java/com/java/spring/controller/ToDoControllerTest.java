package com.java.spring.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.java.spring.model.ToDoItem;
import com.java.spring.service.ToDoService;

@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest
{
	@Mock
	private ToDoService toDoService;
	
	@InjectMocks
	private ToDoController toDoController;
	
	
	private MockMvc mockMvc;
	private ObjectWriter objectWriter = (new ObjectMapper()).writer();
	
	// Sample objects to work with
	ToDoItem item1 = new ToDoItem(1, "Item 01");
	ToDoItem item2 = new ToDoItem(2, "Item 02");
	ToDoItem item3 = new ToDoItem(3, "Item 03");
	
	@BeforeEach
	public void setUp()
	{
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
	}
	
	@Test
	public void getAllItems_success() throws Exception
	{
	
		// Mock (Repliate) the behaviour of the dependent method calls (with params and return)
		Mockito.when(toDoService.getAllItems()).thenReturn(List.of(this.item1, this.item1, this.item3));
		
		
		// Build Request
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/getAllItems")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		// Call/Perform the request
		this.mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$[0].name", is("Item 01")))
		.andExpect(jsonPath("$[0].id", is(1)));
	}
	
	@Test
	public void getItem_success() throws Exception
	{
		// Mock the method call on which this request call is dependent
		Mockito.when(this.toDoService.getItemById(this.item1.getId())).thenReturn(this.item1);
		
		// Build Request
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/getItem/" + this.item1.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		// Call the request
		this.mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.id", is(this.item1.getId())));
	}
	
	@Test
	public void getItemsByName_success() throws Exception
	{
		// Mock the method call on which this request call is dependent
		Mockito.when(this.toDoService.getItemsByName(this.item1.getName())).thenReturn(List.of(this.item1));
		
		// Build Request
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/getItemsByName?name=" + this.item1.getName())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		// Call the request
		this.mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$[0].id", is(this.item1.getId())));
	}
	
	@Test
	public void addItem_success() throws Exception
	{
		// Temporary modification to the ID
		// To properly mock the toDoService.addItem() as the ID is auto-generated
		// And by default the toDoController.addItem() creates an Item with ID = 0
		this.item1.setId(0);
		
		// Mock the method call on which this request call is dependent
		Mockito.when(this.toDoService.addItem(this.item1)).thenReturn(1);
		
		// Build Request
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/addItem")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.param("name", this.item1.getName());
		
		// Call Request
		this.mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$", is(true)));
	}

	@Test
	public void updateItem_success() throws Exception
	{
		// Update the item
		this.item1.setName("Updated Item Name");
		
		// Mock the method call on which this request call is dependent
		Mockito.when(this.toDoService.updateItem(this.item1)).thenReturn(true);
		
		// Build Request
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/updateItem")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectWriter.writeValueAsString(this.item1));
		
		// Call Request
		this.mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$", is(true)));
	}
	
	@Test
	public void deleteItem_success() throws Exception
	{
		// Mock the method call on which this request call is dependent
		Mockito.when(this.toDoService.deleteItem(this.item1.getId())).thenReturn(this.item1);
		
		// Build Request
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/deleteItem/" + this.item1.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		// Call Request
		this.mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$", is(true)));
	}
}
