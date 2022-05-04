package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @InjectMocks
    DbService service;
    @Mock
    TaskRepository repository;

    @Test
    void shouldGetTasksWithIdTest() {

        assertThrows(TaskNotFoundException.class, ()->service.getTasksWithId(4L));

    }

    @Test
    void shouldGetAllTasks(){

        //Given
        Task task1= new Task(1L,"test", "test");
        Task task2= new Task(2L,"test2", "test2");
        Task task3= new Task(3L, "test3", "test3");
        List<Task> taskList= new ArrayList<>();

        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        //When
        when(service.getAllTasks()).thenReturn(taskList);

        //Then
        assertEquals(3, service.getAllTasks().size());

        //Clean up
        service.deleteAll();
    }

    @Test
    void shouldDeleteTask() {
        //Given
        Task task1= new Task(1L,"test", "test");
        Task task2= new Task(2L,"test2", "test2");
        Task task3= new Task(3L, "test3", "test3");
        List<Task> taskList= new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        //When
        repository.save(task1);
        repository.save(task2);
        repository.save(task3);
        repository.deleteById(1L);

        //Then
        verify(repository,times(3)).save(any(Task.class));
        verify(repository).deleteById(1L);
    }
}
