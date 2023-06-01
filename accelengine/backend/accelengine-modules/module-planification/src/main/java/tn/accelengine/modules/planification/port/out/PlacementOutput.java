package tn.accelengine.modules.planification.port.out;

import java.util.List;

import tn.accelengine.core.extend.AECrudOutputPort;
import tn.accelengine.modules.planification.domain.Ability;
import tn.accelengine.modules.planification.domain.Placement;

public interface PlacementOutput extends AECrudOutputPort<Placement> {
	List<Placement> findAllByAbility(List<Ability> abilities);
}
