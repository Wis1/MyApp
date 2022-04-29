package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

class TaskMapperTest {

    TaskMapper taskMapper= new TaskMapper();

    @Test
    void mapToTask() {

        //Given
        TaskDto taskDto= new TaskDto(23L, "washing","something");

        //When
        Task task= taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(23L, task.getId().longValue());
        assertEquals("washing", task.getTitle());
        assertEquals("something", task.getContent());


    }

    @Test
    void mapToTaskDto() {

        //Given
        Task task= new Task(2L, "meeting","something");

        //When
        TaskDto taskDto= taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(2L, taskDto.getId().longValue());
        assertEquals("meeting", taskDto.getTitle());
        assertEquals("something", taskDto.getContent());
    }

    @Test
    void testMapToTaskDto() {
        //Given
        Optional<Task> taskOptional= Optional.of(new Task(2L, "meeting","something"));

        //When
        TaskDto taskDto= taskMapper.mapToTaskDto(taskOptional);

        //Then
        assertEquals(2L, taskDto.getId().longValue());
        assertEquals("meeting", taskDto.getTitle());
        assertEquals("something", taskDto.getContent());
    }

    @Test
    void mapToTaskDtoList() {

        //Given
        Task task1= new Task(2L, "meeting","something");
        Task task2= new Task(3L, "playing", "computer games");
        List<Task> taskList= new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        //When
        List<TaskDto> taskDtoList= taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(2, taskDtoList.size());
        assertEquals("playing", taskDtoList.get(1).getTitle());
    }
}