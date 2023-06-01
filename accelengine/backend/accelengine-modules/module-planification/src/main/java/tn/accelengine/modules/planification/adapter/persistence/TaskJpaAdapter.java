package tn.accelengine.modules.planification.adapter.persistence;

import tn.accelengine.core.annotations.AEPersistenceAdapter;
import tn.accelengine.core.entities.AEList;
import tn.accelengine.core.extend.AECrudJpaAdapter;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.Task;
import tn.accelengine.modules.planification.port.out.TaskOutput;

@AEPersistenceAdapter

public class TaskJpaAdapter extends AECrudJpaAdapter<Task, TaskJpaRepository> implements TaskOutput {

	public TaskJpaAdapter(TaskJpaRepository taskJpaRepository) {
		super(taskJpaRepository);
	}

	@Override
	public AEList<Task> findAllByTask(OperatorShift StartTime, OperatorShift EndTime) {
		AEList<Task> list = new AEList<>();
		list.setDatas(this.jpaRepository.findAllByTask(StartTime, EndTime));
		return list;
	}

}
