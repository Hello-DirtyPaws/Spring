package com.java.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.spring.model.ToDoItem;

@Repository
public interface ToDoItemRepo extends JpaRepository<ToDoItem, Integer>
{
	@Query(value = "SELECT * FROM ITEMS WHERE NAME = :name", nativeQuery = true)
	public List<ToDoItem> findItemsByName(String name);
}
