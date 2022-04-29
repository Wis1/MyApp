package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class TaskControllerUnitTest {

    @Autowired
    TaskController controller;

    @Test
    void getTasksWithId() throws TaskNotFoundException {
        //Given
        TaskDto taskDto= new TaskDto(5L, "test", "test");
        List<TaskDto> taskDtoList= new ArrayList<>();
        taskDtoList.add(taskDto);

        //When
        controller.createTask(taskDto);

        //then
        assertEquals(ResponseEntity.ok(taskDtoList).getBody().get(0).getTitle(),controller.getTasks().getBody().stream().filter(t-> t.getId().equals(5L)).findFirst().get().getTitle());
    }




}
