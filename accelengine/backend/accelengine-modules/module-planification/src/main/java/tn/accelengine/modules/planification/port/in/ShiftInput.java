package tn.accelengine.modules.planification.port.in;

import java.util.List;

import tn.accelengine.core.extend.AECrudInputPort;
import tn.accelengine.modules.planification.domain.Shift;

public interface ShiftInput extends AECrudInputPort<Shift> {

	Shift findAllbyShifts(List<Long> listUsers);

}
