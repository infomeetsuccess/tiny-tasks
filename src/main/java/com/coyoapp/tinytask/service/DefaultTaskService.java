package com.coyoapp.tinytask.service;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.coyoapp.tinytask.domain.Task;
import com.coyoapp.tinytask.domain.TaskStatus;
import com.coyoapp.tinytask.dto.TaskRequest;
import com.coyoapp.tinytask.dto.TaskResponse;
import com.coyoapp.tinytask.exception.TaskNotFoundException;
import com.coyoapp.tinytask.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {

	private final TaskRepository taskRepository;
	private final MapperFacade mapperFacade;

	@Override
	@Transactional
	public TaskResponse createTask(TaskRequest taskRequest) {
		log.debug("createTask(createTask={})", taskRequest);
		Task task = mapperFacade.map(taskRequest, Task.class);
		return transformToResponse(taskRepository.save(task));
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponse> getTasks() {
		log.debug("getTasks()");
		return taskRepository.findAll().stream().map(this::transformToResponse).collect(toList());
	}

	private TaskResponse transformToResponse(Task task) {
		return mapperFacade.map(task, TaskResponse.class);
	}

	@Override
	@Transactional
	public void deleteTask(String taskId) {
		log.debug("deleteTask(taskId={})", taskId);
		taskRepository.delete(getTaskOrThrowException(taskId));
	}

	private Task getTaskOrThrowException(String taskId) {
		return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
	}

	public Task findTaskByID(String uuid) {
		Optional<Task> taskOpt = taskRepository.findById(uuid);
		if (taskOpt.isPresent())
			return taskOpt.get();
		return new Task();
	}

	public Task updateTaskStatus(String taskID, String taskStatus) {
		Optional<Task> taskOpt = taskRepository.findById(taskID);
		Task task = new Task();
		if (taskOpt.isPresent()) {
			task = taskOpt.get();
			task.setStatus(TaskStatus.valueOf(taskStatus));
		}
		return task;
	}

	public Task updateDueDate(String taskID, Date dueDate) {
		Optional<Task> taskOpt = taskRepository.findById(taskID);
		Task task = new Task();
		if (taskOpt.isPresent()) {
			task = taskOpt.get();
			task.setDueDate(dueDate);
		}
		return task;
	}
}
