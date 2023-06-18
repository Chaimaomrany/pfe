package tn.accelengine.modules.planification.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.accelengine.core.configs.AEProperties;
import tn.accelengine.core.domain.Role;
import tn.accelengine.core.entities.AEAuditingEntity;
import tn.accelengine.modules.planification.config.AEUserInstaller;
import tn.accelengine.modules.std.domain.Account;
import tn.accelengine.modules.std.domain.DictionaryValue;
import tn.accelengine.modules.std.domain.Printer;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = AEProperties.TABLE_APP_MODULE_PREFIX + AEUserInstaller.MOD_CODE + "_user")
public class User extends AEAuditingEntity {

	@NotNull
	private String code;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id")
	private DictionaryValue service;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chief_id")
	private User chief;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = AEProperties.TABLE_JOIN_PREFIX + "user_ability")
	private Set<DictionaryValue> abilities = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = AEProperties.TABLE_JOIN_PREFIX + "printers_user")
	private Set<Printer> printers;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = AEProperties.TABLE_JOIN_PREFIX + "user_abilityy")
	private Set<Ability> abilitiees;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "task_id")
	private Set<Task> tasks = new HashSet<>();
	private Set<Timeslot> unavailableTimeSlotSet;

}
