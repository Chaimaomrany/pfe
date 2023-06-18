package tn.accelengine.modules.planification.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import tn.accelengine.core.utils.DateTimeUtil;

public abstract class DateHelper {

	private DateHelper() {
		throw new IllegalStateException("Utility class");
	}

	public static Date convertDate(Date date1, String string1) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = DateTimeUtil.parse(formatter.format(date1) + " " + string1, "yyyy-MM-dd HH:mm");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static boolean DatesEquality(Date date1, Date date2) {
		long compare = date1.getTime() - date2.getTime();
		if (compare == 0) {
			return true;
		}
		return false;
	}

	public static boolean DatesGT(Date date1, Date date2) {
		long compare = date1.getTime() - date2.getTime();
		if (compare > 0) {
			return true;
		}
		return false;
	}

	public static boolean DatesLT(Date date1, Date date2) {
		long compare = date1.getTime() - date2.getTime();
		if (compare < 0) {
			return true;
		}
		return false;
	}

	public static boolean DateIsWeekEnd(Date date1) {
		LocalDate ld = DateHelper.convertToLocalDateViaInstant(date1);
		DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
		return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
	}

	public static boolean DateIsSunday(Date date1) {
		LocalDate ld = DateHelper.convertToLocalDateViaInstant(date1);
		DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
		return day == DayOfWeek.SUNDAY;
	}

	public static Date returnToFriday(Date date) {

		if (DateHelper.DateIsSunday(date)) {
			return DateHelper.minusDaysToDate(date, 2L);
		} else {
			return DateHelper.minusDaysToDate(date, 1L);
		}
	}

	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date addDaysToDate(Date date, Long numberOfDate) {
		return Date.from(convertToLocalDateViaInstant(date).plusDays(numberOfDate).atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date minusDaysToDate(Date date, Long numberOfDays) {
		return Date.from(convertToLocalDateViaInstant(date).minusDays(numberOfDays).atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date trim(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);

		return calendar.getTime();
	}

	public static Date getFirstDateOfCurrentYear() {
		return toDate(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()));
	}

	public static Date toDate(LocalDate date) {
		Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
}