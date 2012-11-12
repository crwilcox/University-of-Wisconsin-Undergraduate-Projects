public class SimpleDate {
	private int year = 0;
	private int month = 0;
	private int day = 0;
	public int currYear = 0;
	public int currMonth = 0;
	public int currDay = 0;

	public SimpleDate() {
		year = this.currYear;
		month = this.currMonth;
		day = this.currDay;
	}

	// Creates a SimpleDate with the current date.
	public SimpleDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public int day() {
		return day;
	}

	public boolean isValid() {
		if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12)
				&& (day <= 31 && day > 0)) {
			return true;
		}
		if ((month == 4 || month == 6 || month == 9 || month == 11)
				&& (day <= 30 && day > 0)) {
			return true;
		}
		if (year % 100 == 0 && year % 400 != 0 && month == 2 && day <= 28
				&& day > 0) {

		}
		if (year % 4 == 0 && month == 2 && day <= 29 && day > 0) {
			return true;
		}
		if (year % 4 != 0 && month == 2 && day <= 28 && day > 0) {
			return true;
		}
		return false;
	}

	public int month() {
		return month;
	}

	public java.lang.String toString() {
		String date = "";

		if (year < 1000)
			date += "0";
		if (year < 100)
			date += "0";
		if (year < 10)
			date += "0";
		date += year + "-";
		if (month < 10)
			date += "0";
		date += month + "-";
		if(day < 10)
			date += "0";
		date += day;

		date = year + "-" + month + "-" + day;
		return date;
	}

	public int year() {
		return year;
	}

	public int compareTo(SimpleDate otherDate) {
		int otherYear = otherDate.year;
		int otherMonth = otherDate.month;
		int otherDay = otherDate.day;
		
		if (otherDate.isValid()==false || isValid() == false)
			return TaskUtility.INVALID_COMPARISON;
		
		if (this.year> otherYear) 
			return TaskUtility.LATER_DATE;
		
		else if (this.year < otherYear) 
			return TaskUtility.EARLIER_DATE;
	
		else{
			if (this.month> otherMonth)
				return TaskUtility.LATER_DATE;
			
			else if (this.month < otherMonth)
				return TaskUtility.EARLIER_DATE;
		
			
			else {
				if (this.day> otherDay)
					return TaskUtility.LATER_DATE;
				else if (this.day < otherDay)
					return TaskUtility.EARLIER_DATE;
				else
					return TaskUtility.SAME_DATE;
			}
		}
		
		
		
	}
}

