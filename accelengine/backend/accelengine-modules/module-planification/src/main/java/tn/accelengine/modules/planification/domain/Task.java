package tn.accelengine.modules.planification.domain;

import java.util.Date;

import javax.persistence.Entity;
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
@Table(name = AEProperties.TABLE_APP_MODULE_PREFIX + AEUserInstaller.MOD_CODE + "_task")

public class Task extends AEAuditingEntity {
	private Date startDateTime;
	private Date endDateTime;
	// @ManyToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "user_id")
	// private User user;

}
