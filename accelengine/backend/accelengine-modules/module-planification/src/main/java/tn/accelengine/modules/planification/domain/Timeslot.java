package tn.accelengine.modules.planification.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.accelengine.core.configs.AEProperties;
import tn.accelengine.core.entities.AEEntity;
import tn.accelengine.modules.planification.config.AEUserInstaller;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = AEProperties.TABLE_APP_MODULE_PREFIX + AEUserInstaller.MOD_CODE + "_timeslot")
@SequenceGenerator(name = "default_sequence", sequenceName = "Timeslot", allocationSize = 5)
public class Timeslot extends AEEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_sequence")
	@PlanningId
	private Long id;

	private Date date;

	private DayOfWeek dayOfWeek;

	private LocalTime startTime;

	private LocalTime endTime;

	private String description;

	public Timeslot(Date date, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, String description) {
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
	}

	@Override
	public String toString() {
		return date + " " + startTime;
	}

}
