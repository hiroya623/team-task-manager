
package com.example.team_task_manager.service;

import com.example.team_task_manager.entity.Task;
import com.example.team_task_manager.entity.User;
import com.example.team_task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll(String email) {
        return taskRepository
                .findAllByCreatedByEmailOrderByCreatedAtDesc(email);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "タスクが見つかりません。ID: " + id));
    }

    public Task create(Task task, User createdBy) {
        task.setCreatedBy(createdBy);
        return taskRepository.save(task);
    }

    public Task update(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }

    public List<Task> searchByTitle(String email, String title) {
        return taskRepository
                .findAllByCreatedByEmailAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
                        email,
                        title
                );
    }

    public List<Task> searchByStatus(String email, String status) {
        return taskRepository
                .findAllByCreatedByEmailAndStatusOrderByCreatedAtDesc(
                        email,
                        status
                );
    }
}