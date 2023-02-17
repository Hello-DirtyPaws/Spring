package com.java.api;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main
{

	private static final String URL = "https://jsonplaceholder.typicode.com/todos/";
	private static RestTemplate restTemplate = new RestTemplate();
	
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException
	{	
		printUserById(20);
	}
	
	private static void printUserById(int id) throws JsonMappingException, JsonProcessingException
	{
//		String msg = restTemplate.getForObject(URL, String.class);
		
		ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
		
		
		if(response.getStatusCode() == HttpStatus.OK)
		{
			ObjectMapper objectMapper = new ObjectMapper();
			
			List<Map<String, Object>> jsonMap = objectMapper.readValue(response.getBody(), List.class);
			
			for (Map<String, Object> map : jsonMap)
			{
//				if(Integer.parseInt((String) map.get("id").toString()) == id)
				if(map.get("id").toString().equalsIgnoreCase(""+id))
				{
					for (Object key : map.keySet())
					{
						System.out.println(key + " : " + map.get(key));
					}
				}
			}
			//System.out.println("Keys: " + jsonMap[2].keySet());
		}
		else
		{
			System.out.println("Error Fetching the data!");
		}
	}

}
