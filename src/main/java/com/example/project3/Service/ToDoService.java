package com.example.project3.Service;

import com.example.project3.API.ApiException;
import com.example.project3.Model.Todo;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final AuthRepository authRepository;


    public List<Todo> getAllToDos() {
        return toDoRepository.findAll();
    }

    public List<Todo>getMyToDo(Integer userId){
        User user= authRepository.findUserById(userId);
        if(user==null){
            throw new ApiException("user not found");
        }
        List<Todo>toDoList=toDoRepository.findAllByUser(user);
        return toDoList;
    }


    public void addToDo(Integer userId, Todo toDo){
    User user= authRepository.findUserById(userId);
        if(user==null){
        throw new ApiException("User not found");}
        toDo.setUser(user);
        toDoRepository.save(toDo);
    }

    public void updateToDo(Integer id, Integer userId, Todo updatedToDo){
    Todo toDo=toDoRepository.findTodoById(id);
    if(toDo==null){
        throw new ApiException("ToDo not found");
    }
        if(toDo.getUser().getId()!=userId){
            throw new ApiException("Error");
        }
        toDo.setTitle(updatedToDo.getTitle());
        toDo.setStatus(updatedToDo.getStatus());
        toDo.setDescription(updatedToDo.getDescription());
        toDoRepository.save(toDo);
    }

    public void deleteToDo(Integer id,Integer userId){
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Todo toDo = toDoRepository.findTodoById(id);
        if (toDo == null) {
            throw new ApiException("ToDo not found");
        }
        toDoRepository.delete(toDo);
    }


}
