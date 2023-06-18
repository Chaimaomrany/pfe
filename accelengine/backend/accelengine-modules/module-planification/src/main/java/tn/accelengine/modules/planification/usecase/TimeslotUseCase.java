package tn.accelengine.modules.planification.usecase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEUseCase;
import tn.accelengine.core.extend.AECrudUseCase;
import tn.accelengine.modules.planification.domain.Timeslot;
import tn.accelengine.modules.planification.port.in.TimeslotInput;
import tn.accelengine.modules.planification.port.out.TimeslotOutput;
import tn.accelengine.modules.planification.utils.DateHelper;

@Slf4j
@AEUseCase
public class TimeslotUseCase extends AECrudUseCase<Timeslot> implements TimeslotInput {
	private TimeslotOutput timeslotOutput;
	private TimeslotInput timeslotInput;

	public TimeslotUseCase(TimeslotOutput timeslotOutput) {
		super(timeslotOutput);
		this.timeslotOutput = timeslotOutput;
	}

	@Override
	public List<Timeslot> createTimeslotByDate(Date startDate, long workingCycle, boolean withMorningAndNoon) {
		List<Timeslot> timeslots = new ArrayList<>();
		// long numberOfHolidayDays = findAllByBetweenBeginDateAndEndDate(startDate,
		// DateHelper.addDaysToDate(startDate, workingCycle - 1))
		// .stream().mapToLong(holiday ->
		// this.numberOfHolidayDays(holiday.getBeginDate(), holiday.getEndDate()))
		// .sum();
		Date endDate = DateHelper.addDaysToDate(startDate, workingCycle);// numberOfHolidayDays - 1);
		LocalDate start = DateHelper.convertToLocalDateViaInstant(startDate);
		LocalDate end = DateHelper.convertToLocalDateViaInstant(endDate);
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			if (this.isWeekEnd(date)) {
				end = end.plusDays(1);
			} else {
				if (withMorningAndNoon) {
					Timeslot timeslotMorning = new Timeslot(DateHelper.asDate(date), date.getDayOfWeek(),
							LocalTime.of(8, 0), LocalTime.of(12, 0), "Matin");
					timeslots.add(timeslotMorning);
					Timeslot timeslotNoon = new Timeslot(DateHelper.asDate(date), date.getDayOfWeek(),
							LocalTime.of(12, 0), LocalTime.of(18, 0), "Après-midi");
					timeslots.add(timeslotNoon);
				} else {
					Timeslot timeslot = new Timeslot(DateHelper.asDate(date), date.getDayOfWeek(), LocalTime.of(8, 0),
							LocalTime.of(18, 0), "1 Jour");
					timeslots.add(timeslot);
				}
			}
		}
		return this.timeslotOutput.saveAllAndFlush(timeslots);
	}

	private Collection<Timeslot> findAllByBetweenBeginDateAndEndDate(Date startDate, Date addDaysToDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllByIds(List<Long> listId) {
		// TODO Auto-generated method stub

	}

	private long numberOfHolidayDays(Date startDate, Date endDate) {
		long result = 0;
		LocalDate start = convertToLocalDateViaInstant(startDate);
		LocalDate end = convertToLocalDateViaInstant(endDate);
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			if (!this.isWeekEnd(date)) {
				result++;
			}
		}
		return result;
	}

	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private boolean isWeekEnd(LocalDate localDate) {
		return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

}
