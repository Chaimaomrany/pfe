package tn.accelengine.modules.planification.port.in;

import java.util.List;

import tn.accelengine.core.extend.AECrudInputPort;
import tn.accelengine.modules.planification.domain.Ability;
import tn.accelengine.modules.planification.domain.Placement;

public interface PlacementInput extends AECrudInputPort<Placement> {
	List<Placement> findAllByAbility(List<Ability> abilities);
}
