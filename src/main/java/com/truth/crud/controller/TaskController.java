package com.truth.crud.controller;

import com.truth.crud.Model.Task;
import com.truth.crud.Repository.CrudRepository;
import com.truth.crud.Service.dto.TaskDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private CrudRepository crudRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDto){
        Task existingTasks = crudRepository.findByNameAndInstructionAndPriority(
                taskDto.getName(), taskDto.getInstruction(), taskDto.getPriority());
        if (existingTasks != null) {
            return new ResponseEntity<>("Duplicate task entry", HttpStatus.BAD_REQUEST);
        }
        Task task = modelMapper.map(taskDto, Task.class);
        crudRepository.save(task);
        return new ResponseEntity<Task>(task,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody TaskDTO taskDto){
        Task existingTask = crudRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Task not found"));

        modelMapper.map(taskDto, existingTask);
        Task updatedTask = crudRepository.save(existingTask);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllTask(){
        List<Task> tasks = crudRepository.findAll();
//        List<TaskDTO> taskDto = tasks.stream()
//                .map(task -> modelMapper.map(task, TaskDTO.class))
//                .collect(Collectors.toList());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskNumber}")
    public ResponseEntity<?> getById(@PathVariable int taskNumber){
        Task task = crudRepository.findById(taskNumber)
                .orElseThrow(()-> new IllegalArgumentException("task not found"));

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/priority/{pNum}")
    public ResponseEntity<?> getTaskByPriority(@PathVariable int pNum){
        List<Task> tasks = crudRepository.findByPriorityGreaterThanEqualOrderByPriorityDesc(pNum);
        List<TaskDTO> taskDto = tasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
        }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteTaskByName(@PathVariable String name){
        Task task = crudRepository.findByName(name);
        crudRepository.delete(task);
        return new ResponseEntity<>("Successfully Delted", HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable int id){
        Task task = crudRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Task not found"));
        crudRepository.delete(task);
        return new ResponseEntity<>("Successfully Delted", HttpStatus.ACCEPTED);
    }
}
