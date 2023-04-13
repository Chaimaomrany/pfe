package tn.accelengine.modules.planification.adapter.persistence;

import tn.accelengine.core.annotations.AEPersistenceAdapter;
import tn.accelengine.core.extend.AECrudJpaAdapter;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.port.out.OperatorShiftOutput;

@AEPersistenceAdapter
class OperatorShiftJpaAdapter extends AECrudJpaAdapter<OperatorShift, OperatorShiftJpaRepository>
		implements OperatorShiftOutput {

	public OperatorShiftJpaAdapter(OperatorShiftJpaRepository operatorShiftJpaRepository) {
		super(operatorShiftJpaRepository);
	}

}
