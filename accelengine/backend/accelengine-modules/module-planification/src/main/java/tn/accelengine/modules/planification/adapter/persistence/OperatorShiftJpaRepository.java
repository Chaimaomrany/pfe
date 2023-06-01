package tn.accelengine.modules.planification.adapter.persistence;

import java.util.List;

import tn.accelengine.core.extend.AEJpaRepository;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.User;

interface OperatorShiftJpaRepository extends AEJpaRepository<OperatorShift> {

	// Shift findAllByShiftIdInAndDeletedIsFalse(List<Long> listUsers);
	List<OperatorShift> findAllByShift(Long Shift);

	List<OperatorShift> findAllByUser(List<User> users);
}
