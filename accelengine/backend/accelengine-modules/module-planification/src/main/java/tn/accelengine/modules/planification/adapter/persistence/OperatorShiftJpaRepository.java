package tn.accelengine.modules.planification.adapter.persistence;

import tn.accelengine.core.extend.AEJpaRepository;
import tn.accelengine.modules.planification.domain.OperatorShift;

interface OperatorShiftJpaRepository extends AEJpaRepository<OperatorShift> {

	// Shift findAllByShiftIdInAndDeletedIsFalse(List<Long> listUsers);
}
