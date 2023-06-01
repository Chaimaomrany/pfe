package tn.accelengine.modules.planification.adapter.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.accelengine.core.extend.AECrudApi;
import tn.accelengine.modules.planification.domain.Task;
import tn.accelengine.modules.planification.port.in.TaskInput;

@RestController
@RequestMapping(value = TaskAPI.ENDPOINT)
public class TaskAPI extends AECrudApi<Task> {

	public static final String ENDPOINT = ROOT_ENDPOINT + "/user/affectation-task";

	private final TaskInput taskInput;

	public TaskAPI(TaskInput taskInput) {
		super(taskInput);
		this.taskInput = taskInput;
	}
}