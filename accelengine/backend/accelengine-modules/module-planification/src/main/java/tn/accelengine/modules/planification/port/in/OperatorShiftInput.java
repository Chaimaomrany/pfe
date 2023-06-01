package tn.accelengine.modules.planification.port.in;

import java.util.List;

import tn.accelengine.core.extend.AECrudInputPort;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.User;

public interface OperatorShiftInput extends AECrudInputPort<OperatorShift> {
	List<OperatorShift> findAllByShift(Long Shift);

	List<OperatorShift> findAllByUser(List<User> users);
}
