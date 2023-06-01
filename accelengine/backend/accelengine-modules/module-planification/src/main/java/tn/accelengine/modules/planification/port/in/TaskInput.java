package tn.accelengine.modules.planification.port.in;

import tn.accelengine.core.entities.AEList;
import tn.accelengine.core.extend.AECrudInputPort;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.Task;

public interface TaskInput extends AECrudInputPort<Task> {
	AEList<Task> findAllByTask(OperatorShift StartTime, OperatorShift EndTime);
}
