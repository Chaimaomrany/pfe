package tn.accelengine.modules.planification.adapter.persistence;

import tn.accelengine.core.annotations.AEPersistenceAdapter;
import tn.accelengine.core.extend.AECrudJpaAdapter;
import tn.accelengine.modules.planification.domain.Ability;
import tn.accelengine.modules.planification.port.out.AbilityOutput;

@AEPersistenceAdapter
public class AbilityJpaAdapter extends AECrudJpaAdapter<Ability, AbilityJpaRepository> implements AbilityOutput {

	public AbilityJpaAdapter(AbilityJpaRepository abilityJpaRepository) {
		super(abilityJpaRepository);
	}

}
