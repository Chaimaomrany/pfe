package tn.accelengine.modules.planification.adapter.persistence;

import java.util.List;

import tn.accelengine.core.extend.AEJpaRepository;
import tn.accelengine.modules.planification.domain.Ability;
import tn.accelengine.modules.planification.domain.Placement;

public interface PlacementJpaRepository extends AEJpaRepository<Placement> {
	List<Placement> findAllByAbility(List<Ability> abilities);
}
