package CCoin;

public class Timestamp {
	private int month;
	private int day;
	private int year;
	private int hour;
	private int minutes;
	private boolean pm; //false = am, true = pm 
	
	public Timestamp(int month, int day, int year, int hour, int minutes, boolean pm) {
		this.month = month;
		this.day = day;
		this.year = year;
		this.hour = hour;
		this.minutes = minutes;
		this.pm = pm;
	}
	
	public Timestamp() {
		this.month = 0;
		this.day = 0;
		this.year = 0;
		this.hour = 0;
		this.minutes = 0;
		this.pm = false;
	}
	
	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getYear() {
		return year;
	}

	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public boolean isPm() {
		return pm;
	}

	@Override
	public String toString() {
		if (pm)
			return "Date: " + this.month + "/" + this.day + "/" + this.year + ", Time: " + this.hour + ":" + this.minutes + "p.m.";
		return "Date: " + this.month + "/" + this.day + "/" + this.year + ", Time: " + this.hour + ":" + this.minutes + "a.m.";
	}
}
