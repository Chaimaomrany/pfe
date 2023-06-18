package tn.accelengine.modules.planification.port.out;

import java.util.List;

import tn.accelengine.core.extend.AECrudOutputPort;
import tn.accelengine.modules.planification.domain.Timeslot;

public interface TimeslotOutput extends AECrudOutputPort<Timeslot> {
	void deleteAll();

	void deleteAllByIds(List<Long> listId);

}
