///////////////////////////////////////////////////////////////////////////////
// Main Class File:  HW2.java
// File:             DTAttribute.java
// Semester:         Fall 2011
//
// Author:           chris@crwilcox.com
// CS Login:         wilcox
// Lecturer's Name:  Jerry Zhu
//////////////////////////// 80 columns wide //////////////////////////////////
public class DTAttribute {
	String ColumnName;
	int ColumnNumber;
	float MutualInfo;

	/**
	 * Constructor for DTAttribute
	 * 
	 * @param columnNumber
	 *            column number for this attribute in columnNames
	 * @param columnName
	 *            Name of this column in columnNames
	 * @param mutualInfo
	 *            the mutualInfo for this element over all of the attributes in
	 *            its set
	 */
	public DTAttribute(int columnNumber, String columnName, float mutualInfo) {
		ColumnNumber = columnNumber;
		ColumnName = columnName;
		MutualInfo = mutualInfo;
	}
}
