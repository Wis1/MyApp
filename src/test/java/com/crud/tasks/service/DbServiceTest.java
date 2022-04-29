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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @InjectMocks
    DbService service;
    @Mock
    TaskRepository repository;

    @Test
    void getTasksWithIdTest() {

        assertThrows(TaskNotFoundException.class, ()->service.getTasksWithId(4L));

    }

    @Test
    void getAllTasks(){

        //Given
        Task task1= new Task(1L,"test", "test");
        Task task2= new Task(2L,"test2", "test2");
        Task task3= new Task(3L, "test3", "test3");
        List<Task> taskList= new ArrayList<>();

        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        service.saveTask(task1);
        service.saveTask(task2);
        service.saveTask(task3);

        //When
        when(repository.findAll()).thenReturn(taskList);
        List<Task> taskListToCheck= service.getAllTasks();

        //Then
        assertThat(taskListToCheck).isNotNull();
        assertThat(taskListToCheck.size()).isEqualTo(3);

        //Clean up
        service.deleteAll();
    }
}
