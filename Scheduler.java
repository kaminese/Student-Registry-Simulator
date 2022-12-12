// Name: MY CHI DUONG
// Student Number: 500641750

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class Scheduler 
{
    
	TreeMap<String, ActiveCourse> schedule;
	// This schedule treemap contains the course id as the key and the active course object
	public Scheduler(TreeMap<String,ActiveCourse> courses)
	{
	  schedule = courses;
	}
	
	// CPS209 MON 1000 2
	public void setDayAndTime(String courseCode, String day, int startTime, int duration)
	{
		
		if (!schedule.containsKey(courseCode.toUpperCase())) {
			throw new unknownCourseException("Unknown Course: " + courseCode);
			// throw new unkownCourseException(courseCode);
		}
		else if (!day.equalsIgnoreCase("mon") && !day.equalsIgnoreCase("tue") && !day.equalsIgnoreCase("wed") && !day.equalsIgnoreCase("thu") && !day.equalsIgnoreCase("fri")) {
			throw new InvalidDayException("Invalid Lecture Day");
		}  
		else if (duration > 3) {
			throw new InvalidDurationException("Invalid Lecture Duration");
		}
		else if (800 > startTime || startTime > 1600) {
			throw new InvalidTimeException("Invalid Lecture Start Time");
		}
		else if (startTime == 1500 && duration == 3) {
			throw new InvalidTimeException("Invalid Lecture Start Time");
		}
		else if (startTime == 1600 && duration > 1) {
			throw new InvalidTimeException("Invalid Lecture Start Time");
		}
		else if ((startTime == 1400 && duration == 3) || (startTime == 1500 && duration > 2)) {
			throw new InvalidTimeException("Invalid Lecture Start Time");
		}


		for (String key : schedule.keySet()) {
			if (key == courseCode) {
				continue;
			}
			else {
				 if (YesSchedule(courseCode.toUpperCase(), day.toUpperCase(), startTime, duration)){
					throw new LectureTimeCollisionException("Lecture Time Collision");
				}
			}	
		}
		// New Code 
		for (String key : schedule.keySet()) {
			clearSchedule(key);
		}
		//New Code end
		if (schedule.containsKey(courseCode)) {
			ActiveCourse timeslotcourse = schedule.get(courseCode);
			timeslotcourse.lectureDuration = duration;
			timeslotcourse.lectureDay = day;
			timeslotcourse.lectureStart = startTime;
		}
	}

	public boolean YesSchedule(String courseCode, String day, int startTime, int duration) {
		for (ActiveCourse ac : schedule.values()) {
			if (day.equalsIgnoreCase(ac.lectureDay)) {
				int aclectureduration = (ac.lectureStart + (ac.lectureDuration * 100));
				if (((startTime + (duration * 100)) > ac.lectureStart) && (startTime < aclectureduration)) {
					return true;
				}
			}
		}
		return false;
	}

	// Custom Exceptions Start HERE -------------------------------

	public class unknownCourseException extends RuntimeException {
		public unknownCourseException() {} 
	//		("Unknown Course: " + s);
		public unknownCourseException(String message) {
			super(message);
		}
	}

	public class InvalidDayException extends RuntimeException {
		public InvalidDayException() {
			// ("Invalid Lecture Day");
		}
		public InvalidDayException(String message) {
			super(message);
		}
	}

	public class InvalidTimeException extends RuntimeException {
		public InvalidTimeException() {
			// ("Invalid Start Time");
		}
		public InvalidTimeException(String message) {
			super(message);
		}
	}

	public class InvalidDurationException extends RuntimeException {
		public InvalidDurationException() {
			// ("Invalid Lecture Duration");
		}
		public InvalidDurationException(String message) {
			super(message);
		}
	}

	public class LectureTimeCollisionException extends RuntimeException {
		public LectureTimeCollisionException() {
			// ("Lecture Time Collision");
		}
		public LectureTimeCollisionException(String message) {
			super(message);
		}
	}

	// Exceptions Ends -------------------------------

	
	public void clearSchedule(String courseCode)
	{
		if (schedule.containsKey(courseCode)) {
			ActiveCourse ac = schedule.get(courseCode);
			ac.lectureDay = "";
			ac.lectureStart = 0;
			ac.lectureDuration = 0;
		}
	}
		
	public void printSchedule() 
	{
		// mapdays is for obtaining the coloumn index for day in table[time][day]
		TreeMap<String, Integer> mapdays = new TreeMap<String, Integer>();
		mapdays.put("MON", 0);
		mapdays.put("TUE", 1);
		mapdays.put("WED", 2);
		mapdays.put("THU", 3);
		mapdays.put("FRI", 4);

		String[][] table = new String[9][5];
		
		// below is finding the index to insert the scheduled course into the table
		// using an iterator for the keyset, i will iterate through all the keys and check if any of their start times are not zero
		// after, i convert the start time to an index
		Set<String> KeySet = schedule.keySet();
		Iterator<String> iter = KeySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			ActiveCourse ac = schedule.get(key);
			int actime = ac.lectureStart;
			if (actime != 0) {
				int time = ((actime - 800)/100);
				String acday = ac.lectureDay;
				int day = mapdays.get(acday);
				// this for loop is to take into account the duration
				for (int i = 0; i < ac.lectureDuration; i++) {
					table[time + i][day] = key;
				}
				break;
			}
		}
		// table[time][day] = //coursename;
		// below is printing the actual table with spaces
		System.out.println("\n\t Mon \t Tue \t Wed \t Thu \t Fri");
		for (int row = 0; row < table.length; row++) {
			System.out.printf("\n%04d", (row + 8) * 100);
			for (int col = 0; col < table[row].length; col++) {
					if (table[row][col] == null) {
						table[row][col] = "    ";
					}
					System.out.print("\t" + table[row][col]);
					}
				}
				System.out.println("");
	} 
}	

