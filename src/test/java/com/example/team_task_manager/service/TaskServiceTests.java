
package com.example.team_task_manager.service;

import com.example.team_task_manager.entity.Task;
import com.example.team_task_manager.entity.User;
import com.example.team_task_manager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void メールアドレスでタスク一覧を取得できる() {
        String email = "test@example.com";
        List<Task> tasks = List.of(mock(Task.class));

        when(taskRepository.findAllByCreatedByEmailOrderByCreatedAtDesc(email))
                .thenReturn(tasks);

        List<Task> result = taskService.findAll(email);

        assertEquals(tasks, result);
        verify(taskRepository).findAllByCreatedByEmailOrderByCreatedAtDesc(email);
    }

    @Test
    void IDでタスクを取得できる() {
        Long id = 1L;
        Task task = mock(Task.class);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        Task result = taskService.findById(id);

        assertSame(task, result);
    }

    @Test
    void 存在しないIDでは例外が発生する() {
        Long id = 999L;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.findById(id)
        );

        assertEquals("タスクが見つかりません。ID: 999", exception.getMessage());
    }

    @Test
    void 作成者を設定してタスクを登録できる() {
        Task task = mock(Task.class);
        User user = mock(User.class);

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.create(task, user);

        verify(task).setCreatedBy(user);
        verify(taskRepository).save(task);
        assertSame(task, result);
    }

    @Test
    void タスクを更新できる() {
        Task task = mock(Task.class);

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.update(task);

        verify(taskRepository).save(task);
        assertSame(task, result);
    }

    @Test
    void IDでタスクを削除できる() {
        Long id = 1L;
        Task task = mock(Task.class);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        taskService.delete(id);

        verify(taskRepository).delete(task);
    }

    @Test
    void タイトルでタスクを検索できる() {
        String email = "test@example.com";
        String title = "テスト";
        List<Task> tasks = List.of(mock(Task.class));

        when(taskRepository
                .findAllByCreatedByEmailAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
                        email, title))
                .thenReturn(tasks);

        List<Task> result = taskService.searchByTitle(email, title);

        assertEquals(tasks, result);
    }

    @Test
    void ステータスでタスクを検索できる() {
        String email = "test@example.com";
        String status = "進行中";
        List<Task> tasks = List.of(mock(Task.class));

        when(taskRepository
                .findAllByCreatedByEmailAndStatusOrderByCreatedAtDesc(email, status))
                .thenReturn(tasks);

        List<Task> result = taskService.searchByStatus(email, status);

        assertEquals(tasks, result);
    }
}