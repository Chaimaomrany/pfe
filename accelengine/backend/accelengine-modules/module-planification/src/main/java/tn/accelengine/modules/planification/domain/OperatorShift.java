package tn.accelengine.modules.planification.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
public class OperatorShift extends AEAuditingEntity {

	private Date startDatePeriod;

	private Date endDatePeriod;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shift_id")
	private Shift shift;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = AEProperties.TABLE_JOIN_PREFIX + "operator_shift_user")
	private Set<User> users;

}
