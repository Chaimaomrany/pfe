package tn.accelengine.modules.planification.adapter.persistence;

import tn.accelengine.core.annotations.AEPersistenceAdapter;
import tn.accelengine.core.extend.AECrudJpaAdapter;
import tn.accelengine.modules.planification.domain.Shift;
import tn.accelengine.modules.planification.port.out.ShiftOutput;

@AEPersistenceAdapter
class ShiftJpaAdapter extends AECrudJpaAdapter<Shift, ShiftJpaRepository> implements ShiftOutput {

	public ShiftJpaAdapter(ShiftJpaRepository shiftJpaRepository) {
		super(shiftJpaRepository);
	}

	/*
	 * @Override public Shift findAllByShifts(List<Long> listUsers) { return
	 * this.jpaRepository.findAllByShiftIdInAndDeletedIsFalse(listUsers); }
	 * 
	 */
}
