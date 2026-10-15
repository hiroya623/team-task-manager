
package com.example.team_task_manager.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskRequestTests {

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
    void 正常なタスク名なら入力エラーにならない() {
        TaskRequest request = new TaskRequest();
        request.setTitle("テストタスク");

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void タスク名が空欄なら入力エラーになる() {
        TaskRequest request = new TaskRequest();
        request.setTitle("");

        assertTrue(
                validator.validate(request).stream()
                        .anyMatch(error ->
                                error.getMessage().equals("タスク名を入力してください。"))
        );
    }

    @Test
    void タスク名が101文字なら入力エラーになる() {
        TaskRequest request = new TaskRequest();
        request.setTitle("あ".repeat(101));

        assertTrue(
                validator.validate(request).stream()
                        .anyMatch(error ->
                                error.getMessage().equals("タスク名は100文字以内で入力してください。"))
        );
    }

    @Test
    void 不正なステータスなら入力エラーになる() {
        TaskRequest request = new TaskRequest();
        request.setTitle("テストタスク");
        request.setStatus("保留");

        assertTrue(
                validator.validate(request).stream()
                        .anyMatch(error ->
                                error.getMessage().equals("正しいステータスを選択してください。"))
        );
    }
}