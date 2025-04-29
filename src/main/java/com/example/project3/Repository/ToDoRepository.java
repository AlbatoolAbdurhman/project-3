package com.example.project3.Repository;

import com.example.project3.Model.Todo;
import com.example.project3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository <Todo, Integer>{

List<Todo>findAllByUser(User user);

Todo findTodoById(Integer id);
}
