package tn.accelengine.modules.planification.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningListVariable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.accelengine.core.configs.AEProperties;
import tn.accelengine.core.entities.AEAuditingEntity;
import tn.accelengine.modules.planification.config.AEUserInstaller;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = AEProperties.TABLE_APP_MODULE_PREFIX + AEUserInstaller.MOD_CODE + "_operator_shift")
@PlanningEntity

@SequenceGenerator(name = "default_sequence", sequenceName = "OperatorShift", allocationSize = 5)
public class OperatorShift extends AEAuditingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_sequence")
	@PlanningId
	private Long id;
	private Date startDatePeriod;
	private Placement placement;
	private Date endDatePeriod;
	// @PlanningVariable(valueRangeProviderRefs = "employeeRange")
	// private User user = null;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shift_id")
	private Shift shift;
//@PlanningListVariable
	@PlanningListVariable(valueRangeProviderRefs = "employeeRange")

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = AEProperties.TABLE_JOIN_PREFIX + "operator_shift_user")
	private Set<User> users;

}
