import java.util.Scanner;

public class CarLot {
	private static Scanner stdin = new Scanner(System.in);

	private static Vehicle[] lot;

	private static final int MAX_VEHICLES = 10;

	private static final int ALL = 1;

	private static final int CAR = 2;

	private static final int TRUCK = 3;

	private static final int SCOOTER = 4;

	public static void main(String[] args) {
		lot = new Vehicle[MAX_VEHICLES];
		addVehicles();
		int choice, kind;
		do {
			choice = getUserChoice();
			switch (choice) {
			case 1:
				kind = getKind();
				list(kind);
				break;

			case 2:
				kind = getKind();
				System.out.print("Enter the percent discount, e.g. 0.15: ");
				double amt = Double.parseDouble(stdin.nextLine());
				discount(kind, amt);
				break;

			case 3:
				kind = getKind();
				System.out.print("Enter the number of passengers: ");
				int pass = Integer.parseInt(stdin.nextLine());
				search(kind, pass);
				break;

			case 4:
				kind = getKind();
				double low,
				high;
				System.out.print("Enter low end of price range: ");
				low = Double.parseDouble(stdin.nextLine());
				System.out.print("Enter high end of price range: ");
				high = Double.parseDouble(stdin.nextLine());
				search(kind, low, high);
				break;
			}
		} while (choice != 0);
		System.out.println("Goodbye!");
	}

	private static int getUserChoice() {
		int choice = -1;
		do {
			System.out.println("Menu Options:");
			System.out.println("0) Quit");
			System.out.println("1) List vehicles");
			System.out.println("2) Discount vehicles");
			System.out
					.println("3) Search for vehicles with given # passengers");
			System.out.println("4) Search for vehicles in given price range");
			System.out.print("Enter your choice (0 - 4): ");
			choice = Integer.parseInt(stdin.nextLine());
			if (choice < 0 || choice > 4)
				System.out.println("Invalid choice");
		} while (choice < 0 || choice > 4);
		return choice;
	}

	private static int getKind() {
		int kind = -1;
		do {
			System.out.print("What kind: all vehicles, all cars, ");
			System.out.println("all trucks, or all scooters?");
			System.out.print("Enter V, C, T, or S: ");
			char choice = stdin.nextLine().charAt(0);
			switch (choice) {
			case 'V':
				kind = ALL;
				break;
			case 'C':
				kind = CAR;
				break;
			case 'T':
				kind = TRUCK;
				break;
			case 'S':
				kind = SCOOTER;
				break;
			default:
				System.out.println("Invalid choice");
			}
		} while (kind == -1);
		return kind;
	}

	private static void addVehicles() {
		// Add 10 calls to Car, Truck, and Scooter constructors, e.g.,
		lot[0] = new Car(true, 1, 10);
		lot[1] = new Scooter(46432);
		lot[2] = new Truck(365, 1829.94);
		lot[3] = new Truck(134, 18429.94);
		lot[4] = new Car(false, 23, 54321);
		lot[5] = new Scooter(56);
		lot[6] = new Car(true, 5, 354987);
		lot[7] = new Scooter(4643);
		lot[8] = new Truck(10, 18259.94);
		lot[9] = new Scooter(454);
	}

	private static void list(int kind) {
		// Modify this code so it only prints vehicles of the kind indicated.
		// 1=all
		// 2=car
		// 3=truck
		// 4=scooter

		for (int i = 0; i < lot.length; i++) {
			if (kind == ALL)
				System.out.println(lot[i]);
			if (kind == CAR && lot[i] instanceof Car)
				System.out.println(lot[i]);
			if (kind == TRUCK && lot[i] instanceof Truck)
				System.out.println(lot[i]);
			if (kind == SCOOTER && lot[i] instanceof Scooter)
				System.out.println(lot[i]);
		}

	}

	private static void discount(int kind, double percent) {
		// Add code to discount the price by the % indicated for the vehicles
		// of the given kind.
		// 1=all
		// 2=car
		// 3=truck
		// 4=scooter
		double tempPrice;
		for (int i = 0; i < lot.length; i++) {
			if (kind == ALL) {
				tempPrice = lot[i].returnPrice();
				lot[i].changePrice(tempPrice * (percent / 100));
			}
			if (kind == CAR && lot[i] instanceof Car) {
				tempPrice = lot[i].returnPrice();
				lot[i].changePrice(tempPrice * (percent / 100));
			}
			if (kind == TRUCK && lot[i] instanceof Truck) {
				tempPrice = lot[i].returnPrice();
				lot[i].changePrice(tempPrice * (percent / 100));
			}
			if (kind == SCOOTER && lot[i] instanceof Scooter) {
				tempPrice = lot[i].returnPrice();
				lot[i].changePrice(tempPrice * (percent / 100));
			}
		}
	}

	private static void search(int kind, int passengers) {
		// Add code to search for vehicles of the given kind with the given
		// number of passengers. Vehicles found fitting the search parameters
		// are printed out.
		for (int i = 0; i < lot.length; i++) {
			if (kind == ALL && lot[i].returnPassengers() == passengers) {
				System.out.println(lot[i]);
			}
			if (kind == CAR && lot[i] instanceof Car
					&& lot[i].returnPassengers() == passengers) {
				System.out.println(lot[i]);
			}
			if (kind == TRUCK && lot[i] instanceof Truck
					&& lot[i].returnPassengers() == passengers) {
				System.out.println(lot[i]);
			}
			if (kind == SCOOTER && lot[i] instanceof Scooter
					&& lot[i].returnPassengers() == passengers) {
				System.out.println(lot[i]);
			}
		}

	}

	private static void search(int kind, double lowPrice, double highPrice) {
		// Add code to search for vehicles of the given kind in the given price
		// range. Vehicles found fitting the search parameters are printed out.
		for (int i = 0; i < lot.length; i++) {
			if (kind == ALL && lot[i].returnPrice() >= lowPrice
					&& lot[i].returnPrice() <= highPrice) {
				System.out.println(lot[i]);
			}
			if (kind == CAR && lot[i] instanceof Car
					&& lot[i].returnPrice() >= lowPrice
					&& lot[i].returnPrice() <= highPrice) {
				System.out.println(lot[i]);
			}
			if (kind == TRUCK && lot[i] instanceof Truck
					&& lot[i].returnPrice() >= lowPrice
					&& lot[i].returnPrice() <= highPrice) {
				System.out.println(lot[i]);
			}
			if (kind == SCOOTER && lot[i] instanceof Scooter
					&& lot[i].returnPrice() >= lowPrice
					&& lot[i].returnPrice() <= highPrice) {
				System.out.println(lot[i]);
			}
		}
	}
}
