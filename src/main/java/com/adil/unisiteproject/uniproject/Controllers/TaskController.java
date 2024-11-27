package com.adil.unisiteproject.uniproject.Controllers;
import com.adil.unisiteproject.uniproject.Services.CategoryService;
import com.adil.unisiteproject.uniproject.models.AppUser;
import com.adil.unisiteproject.uniproject.models.Category;
import com.adil.unisiteproject.uniproject.models.Task;
import com.adil.unisiteproject.uniproject.Services.TaskService;
import com.adil.unisiteproject.uniproject.Services.AppUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final AppUsersService userService;
    private final CategoryService categoryService;

    @Autowired
    public TaskController(TaskService taskService, AppUsersService userService, CategoryService categoryService) {
        this.taskService = taskService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    // Display all tasks for a user
    @GetMapping
    public String getTasks(Model model) {
        AppUser user = userService.getCurrentUser(); // Assume you have a method to get the logged-in user
        List<Task> tasks = taskService.getTasksForUser(user);
        model.addAttribute("tasks", tasks);
        return "task/list";
    }

    @GetMapping("/add")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        System.out.println(categories);
        return "task/form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute @Valid Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "task/form";
        }

        AppUser currentUser = userService.getCurrentUser();
        taskService.saveTask(task, currentUser, result);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        Task task = taskService.getTask(id);
        if (task == null) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);
        model.addAttribute("categories", categoryService.getAllCategories());  // Use the service method to fetch categories
        return "task/form";
    }

    // Delete a task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}