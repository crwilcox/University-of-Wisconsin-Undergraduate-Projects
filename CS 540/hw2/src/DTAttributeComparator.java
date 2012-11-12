///////////////////////////////////////////////////////////////////////////////
// Main Class File:  HW2.java
// File:             DTAttributeComparator.java
// Semester:         Fall 2011
//
// Author:           chris@crwilcox.com
// CS Login:         wilcox
// Lecturer's Name:  Jerry Zhu
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Comparator;

/**
 * A comparator for DTAttributes that allows them to be sorted based on the
 * value of the Mutual Information Value
 */
public class DTAttributeComparator implements Comparator<DTAttribute> {

	@Override
	public int compare(DTAttribute o1, DTAttribute o2) {
		if (o1.MutualInfo > o2.MutualInfo)
			return -1;
		else if (o1.MutualInfo == o2.MutualInfo) {
			// now sort on column number
			if (o1.ColumnNumber < o2.ColumnNumber)
				return -1;
			else
				return 1;
		} else {
			return 1;
		}
	}
}