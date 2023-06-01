package tn.accelengine.modules.planification.adapter.persistence;

import java.util.List;

import tn.accelengine.core.annotations.AEPersistenceAdapter;
import tn.accelengine.core.extend.AECrudJpaAdapter;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.port.out.OperatorShiftOutput;

@AEPersistenceAdapter
class OperatorShiftJpaAdapter extends AECrudJpaAdapter<OperatorShift, OperatorShiftJpaRepository>
		implements OperatorShiftOutput {

	public OperatorShiftJpaAdapter(OperatorShiftJpaRepository operatorShiftJpaRepository) {
		super(operatorShiftJpaRepository);
	}

	@Override
	public List<OperatorShift> findAllByShift(Long Shift) {
		// TODO Auto-generated method stub
		return this.jpaRepository.findAllByShift(Shift);
	}

	@Override
	public List<OperatorShift> findAllByUser(List<User> users) {
		// TODO Auto-generated method stub
		return this.jpaRepository.findAllByUser(users);
	}

}
