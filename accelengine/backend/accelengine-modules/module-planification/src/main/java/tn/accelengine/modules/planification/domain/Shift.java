package tn.accelengine.modules.planification.domain;

import java.util.Date;

import javax.persistence.Column;
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
@Table(name = AEProperties.TABLE_APP_MODULE_PREFIX + AEUserInstaller.MOD_CODE + "_shift")
public class Shift extends AEAuditingEntity {

	@Column(unique = true)
	private String code;

	private String name;

	private Date startTimeWork;

	private Date endTimeWork;
}
