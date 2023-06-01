package tn.accelengine.modules.planification.adapter.persistence;

import java.util.List;

import tn.accelengine.core.annotations.AEPersistenceAdapter;
import tn.accelengine.core.extend.AECrudJpaAdapter;
import tn.accelengine.modules.planification.domain.Ability;
import tn.accelengine.modules.planification.domain.Placement;
import tn.accelengine.modules.planification.port.out.PlacementOutput;

@AEPersistenceAdapter
public class PlacementJpaAdapter extends AECrudJpaAdapter<Placement, PlacementJpaRepository>
		implements PlacementOutput {

	public PlacementJpaAdapter(PlacementJpaRepository placementjpaRepository) {
		super(placementjpaRepository);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Placement> findAllByAbility(List<Ability> abilities) {
		// TODO Auto-generated method stub
		return this.jpaRepository.findAllByAbility(abilities);
	}

}
