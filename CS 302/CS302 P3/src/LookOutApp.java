import java.util.Scanner;

public class LookOutApp {
	
	private static TaskList myStack;
	private static Task twoTask;
	
	public static void main(String[] args) {
	

	int usr;
	boolean exit=false;
	boolean print=true;
	int yearIn;
	int monthIn;
	int dayIn;

	



		System.out.println("Welcome to LookOut!");
		System.out.println();
		Scanner scan = new Scanner(System.in);

		// CHOOSES TASK IMPLEMENTATION
		System.out.println("Please choose a task list implementation:");
		System.out.println("1. Stack");
		System.out.println("2. Fifo");
		System.out.println("3. Priority Sequence");

		boolean userInput0 = false;
		do {
			System.out.println("Your Choice: ");
			double usr0 = scan.nextDouble();
			int usr1 = (int) usr0;
			if (usr1 != usr0) {
				System.out.println("Out of range.");
			} else if (usr0 == 1) {// Stack
				myStack = new TaskStack();
				userInput0 = true;
			}

			else if (usr0 == 2) {// Fifo
				myStack = new TaskFifo();
				userInput0 = true;
			}

			else if (usr0 == 3) {// Priority Sequence
				myStack = new TaskPrioritySequence();
				userInput0 = true;
			} else {
				System.out.println("Out of range.");
			}

		} while (userInput0 == false);

		// PROGRESSES TO MAIN MENU

		do {
			if (print==true){
				System.out.println("Main Menu. Choose an option");
				System.out.println("1. Display contents of task list");
				System.out.println("2. Print next task");
				System.out.println("3. Remove the next task");
				System.out.println("4. Add a task");
				System.out.println("5. Save task list to disk");
				System.out.println("6. Load task list from disk");
				System.out.println("7. Quit");
			}
			System.out.println("Your Choice: ");
			double usr1 = scan.nextDouble();
			usr = (int) usr1;
			// Checks if double entered can be successfully casted to an int
			if (usr1 != usr){
				System.out.println("Out of range.");
				print=false;
			}
			
			else if (usr1 == usr) {
				//Checks for equal, allowing switch to execute
				//so that a rounded value will not cause execution
				switch (usr) {
				case 1:
					System.out.println("Task Summary: ");
					Task[] printTask = myStack.allTasks();			
					
					
					for (int i = 0; i < myStack.taskCount(); i++){
						String temp = printTask[i].toString();
						System.out.println(temp);
					}	
					print=true;
					break;
				case 2:
					System.out.println(myStack.nextTask());
					print=true;
					break;
				case 3:
					Task temp = myStack.removeNextTask();
					System.out.println(temp);
					System.out.println("Task removed");					
					print=true;
					break;
				case 4:
					//WRITE PROGRAM
					System.out.println("Enter a description of the task ");                            
		            scan.nextLine();
		            String taskDescription = scan.nextLine();
		            
		            System.out.println("Does the task have a deadline? Type 1 for yes or 0 for no: ");        
		            int deadlineInput = scan.nextInt();
		            
		            // invalid input check
		            while (deadlineInput != 1 && deadlineInput != 0){
		                System.out.println("Invalid input");
		                System.out.println("Type 1 for yes or 0 for no: ");
		                deadlineInput = scan.nextInt();
		            }
		            
		            int x = 0;
		            do{
		                // executes once (loops if invalid input)
		            if (deadlineInput == 1){
		                System.out.println("Enter year ");
		                yearIn = scan.nextInt();
		                System.out.println("Enter month (numerical value) ");
		                monthIn = scan.nextInt();
		                System.out.println("Enter day ");
		                dayIn = scan.nextInt();
		                scan.nextLine();
		                
		            SimpleDate newDate = new SimpleDate(yearIn, monthIn, dayIn);
		            // checking for invalid
		            if (newDate.isValid() == true){
		                x = 0;
		                twoTask = new Task(taskDescription, newDate);            
		               myStack.addTask(twoTask);
		                }
		            else if (newDate.isValid() == false){
		                System.out.println("Invalid date entry");
		                System.out.println("Please try again");
		                 x = 1;
		            }
		        
		            }
		            
		            if(deadlineInput == 0){
		            twoTask = new Task(taskDescription);
		            myStack.addTask(twoTask);
		            }
		            
		        }while (x == 1);    
		            
					print=true;
					break;
				case 5:
					System.out.println("What would you like to name your file? ");
					String fileName = scan.next();
					scan.nextLine();
					
					TaskUtility.saveTasksToFile(fileName, myStack.allTasks());
					
					if (TaskUtility.saveTasksToFile(fileName, myStack.allTasks()) == true )
						{System.out.println("File saved successfully. ");}
					else
						{System.out.println("File did not save correctly. ");}
					print=true;
					break;
				case 6:
					System.out.println("What was the name of your file? ");
					String loadFileName = scan.next();
					scan.nextLine();
					
					if (TaskUtility.readTasksFromFile(loadFileName) == null)
						{System.out.println("File was not loaded successfully. ");}
					else
						{
						myStack.initWithTasks(TaskUtility.readTasksFromFile(loadFileName));
						System.out.println("File loaded successfully. ");}
					print=true;
					break;
				case 7:
					System.out.println("Goodbye!");
					System.exit(0);
					exit=true;
					print=true;
					break;
				default:
					System.out.println("Out of Range");
					print=false;
				}
			}
		} while (exit != true);

	}
}



