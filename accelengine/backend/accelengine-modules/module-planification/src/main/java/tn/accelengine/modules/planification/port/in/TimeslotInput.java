package tn.accelengine.modules.planification.port.in;

import java.util.Date;
import java.util.List;

import tn.accelengine.core.extend.AECrudInputPort;
import tn.accelengine.modules.planification.domain.Timeslot;

public interface TimeslotInput extends AECrudInputPort<Timeslot> {
	List<Timeslot> createTimeslotByDate(Date date, long workingCycle, boolean withMorningAndNoon);

	void deleteAll();

	void deleteAllByIds(List<Long> listId);

}
