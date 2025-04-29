package com.example.project3.Controller;

import com.example.project3.Model.Todo;
import com.example.project3.Model.User;
import com.example.project3.Service.ToDoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor

public class ToDoController {


    private final ToDoService toDoService;

    @GetMapping("/my-todos")
    public ResponseEntity getMyToDos(@AuthenticationPrincipal User user) {
        List<Todo> todos = toDoService.getMyToDo(user.getId());
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/all-todos")
    public ResponseEntity getAllToDos() {
        List<Todo> todos = toDoService.getAllToDos();
        return ResponseEntity.ok(todos);
    }


    @PostMapping("/add")
    public ResponseEntity addToDo(@AuthenticationPrincipal User user, @RequestBody @Valid Todo toDo) {
        toDoService.addToDo(user.getId(), toDo);
        return ResponseEntity.ok("ToDo added successfully");
    }


    @PutMapping("/update/{todoId}")
    public ResponseEntity updateToDo(@AuthenticationPrincipal User user, @PathVariable Integer todoId, @RequestBody @Valid Todo toDo) {
        toDoService.updateToDo(user.getId(), todoId, toDo);
        return ResponseEntity.ok("ToDo updated successfully");
    }

    @DeleteMapping("/delete/{todoId}")
    public ResponseEntity deleteToDo(@AuthenticationPrincipal User user, @PathVariable Integer todoId) {
        toDoService.deleteToDo(user.getId(), todoId);
        return ResponseEntity.ok("ToDo deleted successfully");
    }
}
