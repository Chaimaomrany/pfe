package tn.accelengine.modules.planification.adapter.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.accelengine.core.extend.AEJpaRepository;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.Task;

public interface TaskJpaRepository extends AEJpaRepository<Task> {

	@Query(value = "select '*' from OperatorShift d")
	List<Task> findAllByTask(@Param("operatorShiftId") OperatorShift startDatePeriod,
			@Param("operatorShiftId") OperatorShift endDatePeriod);
}
