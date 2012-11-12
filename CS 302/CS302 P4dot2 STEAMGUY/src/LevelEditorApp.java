import java.util.Scanner;

/**
 * This application launches a Window that contains a LevelEditor.
 * 
 * @author Tim
 * 
 */
public class LevelEditorApp {
	private LevelEditorApp() {
		// javadoc prevention
	}

	/**
	 * The entry point for the LevelEditor application
	 */
	public static void main(String[] args) {
		Scanner stdin=new Scanner(System.in);
		
		System.out.println("Please enter a width for the level.");		
		int width = getIntInRange(stdin,2, 30);
		
		System.out.println("Please enter a height for the level.");		
		int height = getIntInRange(stdin,2, 25);

		System.out.println("Please enter name for saving this level.");	
		String filename=stdin.nextLine();
		
		LevelEditor le = new LevelEditor(width, height, filename);
		Window w = new Window(le);
		w.showWindow("Level Editor");
	}

	/**
	 * Get a number from sc that is in the range min to max.
	 * 
	 * @param sdtin
	 *            The scanner to use
	 * @param min
	 *            the minimum value
	 * @param max
	 *            the maximum vale
	 * @return An int from the Scanner between min and max.
	 */
	private static int getIntInRange(Scanner sdtin, int min, int max) {
		//Loop forever (until we use return to quit out)
		while (true) {
			System.out.print("Enter a number between " + min + " and "
					+ max + ": ");
			String valueString = sdtin.nextLine();
			try {
				int value = Integer.parseInt(valueString);
				if (value >= min && value <= max) {
					return value;
				} else {
					System.out.println("'"+value + "' is not in the correct range.\n");
				}
			} catch (NumberFormatException nfe) {
				System.out.println("'"+valueString + "' is not an integer.\n");
			}
		}
	}
}
