
package com.example.team_task_manager.repository;

import com.example.team_task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByOrderByCreatedAtDesc();

    List<Task> findAllByCreatedByEmailOrderByCreatedAtDesc(String email);

    List<Task> findAllByCreatedByEmailAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
        String email,
        String title
    );

    List<Task> findAllByCreatedByEmailAndStatusOrderByCreatedAtDesc(
        String email,
        String status
    );
}