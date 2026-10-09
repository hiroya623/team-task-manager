
package com.example.team_task_manager.controller;

import com.example.team_task_manager.entity.Task;
import com.example.team_task_manager.entity.User;
import com.example.team_task_manager.repository.UserRepository;
import com.example.team_task_manager.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.team_task_manager.dto.TaskRequest;
import com.example.team_task_manager.dto.TaskUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.util.List;

import java.time.LocalDate;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(
            TaskService taskService,
            UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @GetMapping("/tasks")
    public String listTasks(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String status,
            Model model,
            Authentication authentication
    ) {
        List<Task> tasks;

        if (!status.isEmpty()) {
            tasks = taskService.searchByStatus(authentication.getName(), status);

            if (!keyword.isEmpty()) {
                tasks = tasks.stream()
                        .filter(task -> task.getTitle()
                                .toLowerCase()
                                .contains(keyword.toLowerCase()))
                        .toList();
            }
        } else {
            tasks = taskService.searchByTitle(authentication.getName(), keyword);
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);

        return "tasks";
    }

    @PostMapping("/tasks")
    public String createTask(
        @Valid @ModelAttribute("task") TaskRequest request,
        BindingResult bindingResult,
        Authentication authentication,
        Model model
    ) {

        if (!java.util.List.of("未着手", "進行中", "完了")
                .contains(request.getStatus())) {
            bindingResult.rejectValue(
                    "status",
                    "invalid.status",
                    "正しいステータスを選択してください。"
            );
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "tasks",
                    taskService.findAll(authentication.getName())
            );
            return "tasks";
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "ユーザーが見つかりません。"
                ));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        taskService.create(task, user);

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/edit")
    public String showEditForm(
        @org.springframework.web.bind.annotation.PathVariable Long id,
        Model model,
        Authentication authentication
    ) {
        Task task = taskService.findById(id);
        checkTaskOwner(task, authentication);

        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle(task.getTitle());
        request.setDescription(task.getDescription());
        request.setStatus(task.getStatus());
        request.setDueDate(task.getDueDate());

        model.addAttribute("task", request);
        model.addAttribute("taskId", id);

        return "task-edit";
    }

    @PostMapping("/tasks/{id}/edit")
    public String updateTask(
            @org.springframework.web.bind.annotation.PathVariable Long id,
            @Valid @ModelAttribute("task") TaskUpdateRequest request,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        Task existingTask = taskService.findById(id);
        checkTaskOwner(existingTask, authentication);

        if (!java.util.List.of("未着手", "進行中", "完了")
                .contains(request.getStatus())) {
            bindingResult.rejectValue(
                    "status",
                    "invalid.status",
                    "正しいステータスを選択してください。"
            );
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("taskId", id);
            return "task-edit";
        }

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setStatus(request.getStatus());
        existingTask.setDueDate(request.getDueDate());

        taskService.update(existingTask);

        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(
            @org.springframework.web.bind.annotation.PathVariable Long id,
            Authentication authentication) {

        Task task = taskService.findById(id);
        checkTaskOwner(task, authentication);

        taskService.delete(id);

        return "redirect:/tasks";
    }

    private void checkTaskOwner(Task task, Authentication authentication) {
        if (!task.getCreatedBy().getEmail().equals(authentication.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "このタスクを操作する権限がありません。"
            );
        }
    }
}