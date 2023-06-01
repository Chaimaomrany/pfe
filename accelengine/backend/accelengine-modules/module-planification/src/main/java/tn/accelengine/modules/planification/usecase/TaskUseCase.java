package tn.accelengine.modules.planification.usecase;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEUseCase;
import tn.accelengine.core.entities.AEList;
import tn.accelengine.core.extend.AECrudUseCase;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.Task;
import tn.accelengine.modules.planification.port.in.TaskInput;
import tn.accelengine.modules.planification.port.out.TaskOutput;

@Slf4j
@AEUseCase
public class TaskUseCase extends AECrudUseCase<Task> implements TaskInput {

	private TaskOutput taskOutput;
	private TaskInput taskInput;

	public TaskUseCase(TaskOutput taskOutput) {
		super(taskOutput);
		this.taskOutput = taskOutput;
	}

	@Override
	public AEList<Task> findAllByTask(OperatorShift StartTime, OperatorShift EndTime) {
		log.info("findAllByTimeslot, {}", StartTime, EndTime);
		this.authorization.assertCanRead(Task.class);
		return this.taskInput.findAllByTask(StartTime, EndTime);
	}

}
