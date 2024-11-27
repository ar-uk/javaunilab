package com.adil.unisiteproject.uniproject.Services;

import com.adil.unisiteproject.uniproject.Repository.CategoryRepository;
import com.adil.unisiteproject.uniproject.Repository.TaskRepository;
import com.adil.unisiteproject.uniproject.models.AppUser;
import com.adil.unisiteproject.uniproject.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Task> getTasksForUser(AppUser user) {
        return taskRepository.findByUser(user);
    }

    public Task saveTask(Task task, AppUser user, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }

        task.setUser(user);
        return taskRepository.save(task);
    }
    public void updateTask(Task task) {
        Task existingTask = taskRepository.findById(task.getId()).orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setDueDate(task.getDueDate());
            existingTask.setStatus(task.getStatus());
            existingTask.setPriority(task.getPriority());
            existingTask.setCategory(task.getCategory());
            taskRepository.save(existingTask);
        }
    }
    // Delete a task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Get a specific task
    public Task getTask(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
}