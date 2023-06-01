package tn.accelengine.modules.planification.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = AEProperties.TABLE_APP_MODULE_PREFIX + AEUserInstaller.MOD_CODE + "_placement")
public class Placement extends AEAuditingEntity {
	private String name;
	private Ability requiredSkill;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = AEProperties.TABLE_JOIN_PREFIX + "placement_ability")
	private Set<Ability> abilities;

}
