package tn.accelengine.modules.planification.port.out;

import java.util.List;

import tn.accelengine.core.extend.AECrudOutputPort;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.User;

public interface OperatorShiftOutput extends AECrudOutputPort<OperatorShift> {
	List<OperatorShift> findAllByShift(Long Shift);

	List<OperatorShift> findAllByUser(List<User> users);
}
