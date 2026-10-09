
package com.example.team_task_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import jakarta.validation.constraints.Pattern;

public class TaskRequest {

    @NotBlank(message = "タスク名を入力してください。")
    @Size(max = 100, message = "タスク名は100文字以内で入力してください。")
    private String title;

    @Size(max = 2000, message = "説明は2000文字以内で入力してください。")
    private String description;

    @Pattern(
            regexp = "未着手|進行中|完了",
            message = "正しいステータスを選択してください。"
    )
    private String status = "未着手";

    private LocalDate dueDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}