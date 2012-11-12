///////////////////////////////////////////////////////////////////////////////
// Main Class File:  HW2.java
// File:             DTTree.java
// Semester:         Fall 2011
//
// Author:           chris@crwilcox.com
// CS Login:         wilcox
// Lecturer's Name:  Jerry Zhu
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;

public class DTTree {
	String attributeName;
	String Choice;
	int AttributeNumber;
	ArrayList<DTTree> Children;
	boolean collapsed;

	/**
	 * Constructor for DTTree
	 * 
	 * @param attributeName
	 *            name of the attribute this node is going to look at
	 * @param choice
	 *            decisions made to get to this node, (root node is labeled
	 *            'root' or null.
	 * @param attributeNumber
	 *            the columnNumber of this attribute. aligns with the number in
	 *            columnNames of DecisionTree.java
	 * @param children
	 *            The Children of this node...
	 */
	public DTTree(String attributeName, String choice, int attributeNumber,
			ArrayList<DTTree> children) {
		this.attributeName = attributeName;
		AttributeNumber = attributeNumber;
		Children = children;
		Choice = choice;
		collapsed = false;

	}

	// TODO: Decide if i need this
	public DTTree(DTTree dtTree) {

		this.attributeName = dtTree.attributeName;
		AttributeNumber = dtTree.AttributeNumber;

		Children = new ArrayList<DTTree>();
		if (dtTree.Children != null) {
			for (int i = 0; i < dtTree.Children.size(); i++) {
				Children.add(new DTTree(dtTree.Children.get(i)));
			}
		}

		Choice = dtTree.Choice;
		collapsed = dtTree.collapsed;
	}

	/**
	 * Prints the Decision Tree in ASCII Form
	 */
	public void PrintTree() {
		PrintTree(0);
	}

	/**
	 * Prints the Decision Tree in ASCII Form
	 * 
	 * @param Tabs
	 *            number of tabs to prepend the entry with
	 */
	private void PrintTree(int Tabs) {
		String whitespace = "";
		for (int i = 0; i < Tabs; i++) {
			whitespace += "\t";
		}
		if (Choice == null) {
			Choice = "ROOT";
		}

		if (this.Children != null && this.Children.size() != 0) {
			System.out.println(whitespace + Choice + " {" + this.attributeName
					+ "?}");
			for (int i = 0; i < this.Children.size(); i++) {
				DTTree child = this.Children.get(i);
				if (child != null)
					child.PrintTree(Tabs + 1);
			}
		} else {
			System.out.println(whitespace + Choice + " (" + this.attributeName
					+ ")");
		}
	}

	/**
	 * used by prune trees to monitor where it has been.
	 */
	public void ClearAllCollapsed() {
		collapsed = false;
		if (Children != null) {
			for (int i = 0; i < Children.size(); i++) {
				Children.get(i).ClearAllCollapsed();
			}
		}
	}
}
