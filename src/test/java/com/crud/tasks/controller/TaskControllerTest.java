package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper mapper;

    @Test
    void shouldGetTasks() throws Exception {

        //Given
        List<Task> taskList = List.of(new Task(2L, "title1", "content"));
        List<TaskDto> taskDtoList = List.of(new TaskDto(2L, "title1", "content"));
        when(service.getAllTasks()).thenReturn(taskList);
        when(mapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(2)))
                .andExpect(jsonPath("$[0].title", Matchers.is("title1")))
                .andExpect(jsonPath("$[0].content", Matchers.is("content")));
    }

    @Test
    void shouldGetTaskWithId() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(3L, "title", "content");
        Task task = new Task(3L, "title", "content");

        when(mapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.getTasksWithId(3L)).thenReturn(task);

        //When @ Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/{taskId}", 3L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(3)))
                .andExpect(jsonPath("$.title", Matchers.is("title")))
                .andExpect(jsonPath("$.content", Matchers.is("content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(3L, "title", "content");
        Task task = new Task(3L, "title", "content");

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/{taskId}", 3L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(3L, "title", "content");
        Task task = new Task(3L, "title", "content");
        Task updateTask = new Task(4L, "title1", "content1");
        TaskDto updateTaskDto = new TaskDto(4L, "title1", "content1");

        when(mapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(updateTask);
        when(mapper.mapToTaskDto(any(Task.class))).thenReturn(updateTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("content1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("title1")));
    }

    @Test
    void shouldCreateTask() throws Exception{

        //Given
        TaskDto newTaskDto = new TaskDto(3L, "title", "content");
        Task newTask = new Task(3L, "title", "content");

        when(mapper.mapToTask(any(TaskDto.class))).thenReturn(newTask);
        when(service.saveTask(any(Task.class))).thenReturn(newTask);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(newTaskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
