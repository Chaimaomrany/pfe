package tn.accelengine.modules.planification.port.out;

import java.util.List;

import tn.accelengine.core.extend.AECrudOutputPort;
import tn.accelengine.modules.planification.domain.Shift;

public interface ShiftOutput extends AECrudOutputPort<Shift> {

	Shift findAllByShifts(List<Long> listUsers);
}
