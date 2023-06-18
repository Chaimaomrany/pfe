package tn.accelengine.modules.planification.usecase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEUseCase;
import tn.accelengine.core.extend.AECrudUseCase;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.Timeslot;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.port.in.OperatorShiftInput;
import tn.accelengine.modules.planification.port.out.OperatorShiftOutput;
import tn.accelengine.modules.planification.utils.DateHelper;

@Slf4j
@AEUseCase
public class OperatorShiftUseCase extends AECrudUseCase<OperatorShift> implements OperatorShiftInput {

	private OperatorShiftOutput operatorShiftOutput;

	public OperatorShiftUseCase(OperatorShiftOutput operatorShiftOutput) {
		super(operatorShiftOutput);
		this.operatorShiftOutput = operatorShiftOutput;
	}

	@Override
	public List<OperatorShift> findAllByShift(Long Shift) {
		// TODO Auto-generated method stub
		return null;
	}

	private void initWorkingCycleByUserFromBeginDateToEndDateByCycle(Set<User> users, Date startDate, Date endDate,
			int numberOfDayPerWorkingCycle) {

		LocalDate start = DateHelper.convertToLocalDateViaInstant(startDate);
		LocalDate end = DateHelper.convertToLocalDateViaInstant(endDate);
		OperatorShift operatorshift = null;
		List<Timeslot> timeslots = new ArrayList<>();
		int numberOfDay = 0;
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			boolean isNotWorkingDay = this.isWeekEnd(date);
			if (!isNotWorkingDay) {
				Timeslot timeslot = new Timeslot(DateHelper.asDate(date), date.getDayOfWeek(), LocalTime.of(8, 0),
						LocalTime.of(18, 0), "1 Jour");
				timeslots.add(timeslot);
				numberOfDay++;
			}
			if (operatorshift == null && !isNotWorkingDay) {
				operatorshift = new OperatorShift();
				operatorshift.setStartDatePeriod(DateHelper.asDate(date));
				operatorshift.setUsers(users);
			}
			if ((numberOfDay == numberOfDayPerWorkingCycle || end.isEqual(date)) && !isNotWorkingDay) {
				operatorshift.setEndDatePeriod(DateHelper.asDate(date));
				operatorshift.setTimeslots(timeslots);
				this.operatorShiftOutput.saveAndFlush(operatorshift);
				operatorshift = null;
				numberOfDay = 0;
				timeslots = new ArrayList<>();
			}
		}
	}

	@Override
	public List<OperatorShift> findAllByUser(List<User> users) {
		// TODO Auto-generated method stub

		return this.operatorShiftOutput.findAllByUser(users);
	}

	private List<OperatorShift> findAllOperatorshiftFromDate(List<OperatorShift> operatorshifts, Date date) {
		List<OperatorShift> result = new ArrayList<>();
		operatorshifts.forEach(operatorshift -> {
			if (operatorshift.getStartDatePeriod().getTime() >= date.getTime()) {
				result.add(operatorshift);
			}
		});
		return result;
	}

	private boolean isWeekEnd(LocalDate localDate) {
		return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

}
