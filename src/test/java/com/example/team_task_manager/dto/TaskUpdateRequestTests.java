
package com.example.team_task_manager.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskUpdateRequestTests {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void 正常な入力ならエラーにならない() {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("更新後のタスク");
        request.setDescription("更新後の説明");
        request.setStatus("進行中");

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void タスク名が空欄ならエラーになる() {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("");
        request.setStatus("未着手");

        assertTrue(
                validator.validate(request).stream()
                        .anyMatch(error ->
                                error.getMessage().equals("タスク名を入力してください。"))
        );
    }

    @Test
    void ステータスが空欄ならエラーになる() {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("更新後のタスク");
        request.setStatus("");

        assertTrue(
                validator.validate(request).stream()
                        .anyMatch(error ->
                                error.getMessage().equals("ステータスを選択してください。"))
        );
    }

    @Test
    void 不正なステータスならエラーになる() {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("更新後のタスク");
        request.setStatus("保留");

        assertTrue(
                validator.validate(request).stream()
                        .anyMatch(error ->
                                error.getMessage().equals("正しいステータスを選択してください。"))
        );
    }

    @Test
    void 説明が2001文字ならエラーになる() {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("更新後のタスク");
        request.setDescription("あ".repeat(2001));
        request.setStatus("未着手");

        assertTrue(
                validator.validate(request).stream()
                        .anyMatch(error ->
                                error.getMessage().equals("説明は2000文字以内で入力してください。"))
        );
    }
}