package tn.accelengine.modules.planification.adapter.persistence;

import java.util.List;

import tn.accelengine.core.extend.AEJpaRepository;
import tn.accelengine.modules.planification.domain.Shift;

interface ShiftJpaRepository extends AEJpaRepository<Shift> {

	Shift findAllByShiftIdInAndDeletedIsFalse(List<Long> listUsers);
}
