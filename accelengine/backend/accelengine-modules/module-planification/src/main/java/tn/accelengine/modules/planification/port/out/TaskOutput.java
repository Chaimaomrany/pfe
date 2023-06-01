package tn.accelengine.modules.planification.port.out;

import tn.accelengine.core.entities.AEList;
import tn.accelengine.core.extend.AECrudOutputPort;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.Task;

public interface TaskOutput extends AECrudOutputPort<Task> {
	AEList<Task> findAllByTask(OperatorShift StartTime, OperatorShift EndTime);
}
