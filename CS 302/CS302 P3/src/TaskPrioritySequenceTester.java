public class TaskPrioritySequenceTester {
	public static void main(String[] args) {

		TaskList t = new BrokenPrioritySequence();

		// TESTING: t.taskCount()
		if (t.taskCount() != 0) {
			System.out.print("taskCount test 1 Failed: != 0 Initially. ");
			System.out.println("May be Problem With Constructor.");
		}

		// TESTING: t.isEmpty
		if (t.isEmpty() != true)
			System.out.println("isEmpty test 1 Fails: Initially Empty");

		// TESTING : t.initWithTasks(), which uses t.addTask
		int tempTaskCount = t.taskCount();
		Task[] k = new Task[4]; 
		SimpleDate date1 = new SimpleDate(2006, 3, 4);
		SimpleDate date3 = new SimpleDate(2008, 5, 12);
		SimpleDate date4 = new SimpleDate(2243, 10, 20);

		Task t1 = new Task("go to school", date1);
		Task t2 = new Task("empty trash", date3);
		Task t3 = new Task("hate life");
		Task t4 = new Task("hate java", date4);

		k[0] = t1;
		k[1] = t2;
		k[2] = t3;
		k[3] = t4;

		t.initWithTasks(k);
		if (t.isEmpty() == true)
			System.out.println("Add Task Failed 1: Array is Empty");
		if (t.taskCount() != (tempTaskCount + 4))
			System.out
					.println("Add Task Failed 1: Task Count Not Incremented Correctly");

		// TESTING: t. addTask()
		SimpleDate meh = new SimpleDate(2008, 4, 14);
		Task kt = new Task("watch anime", meh);
		t.addTask(kt);
		if (t.isEmpty() == true)
			System.out.println("Add Task Failed 2: Array is not Empty");
		if (t.taskCount() != (tempTaskCount + 5))
			System.out
					.println("Add Task Failed 2: Task Count Not Incremented Correctly");

		Task[] list = t.allTasks();
		if (t.nextTask() != t1)
			System.out.println("nextTask test 1 failed: not ordered correctly");

		// Tests t.removesNextTask and t.nextTask

		if (t.removeNextTask() != list[list.length - 1])
			System.out.println("removeNextTask test 1 Failed");

		if (t.nextTask() != list[list.length - 2])
			System.out.println("nextTask Failed");

		if (t.removeNextTask() != list[list.length - 2])
			System.out.println("removeNextTask test 2 failed");

		if (t.nextTask() != list[list.length - 3])
			System.out.println("nextTask test 2 failed");

		if (t.taskCount() != 3)
			System.out.println("taskCount test 2 failed");

	}
}
