public class TaskPrioritySequence implements TaskList {
	private Task[] priorityArray;
	private int taskNumber;

	public TaskPrioritySequence() {
		priorityArray = new Task[20];
		taskNumber = 0;
	}

	/*
	 * Returns the number of tasks currently in the list.
	 * 
	 * @see TaskList#taskCount()
	 */
	public int taskCount() {
		return taskNumber;
	}

	/*
	 * Returns whether the task list is empty, i.e. whether the result of
	 * taskCount() is 0.
	 * 
	 * @see TaskList#isEmpty()
	 */
	public boolean isEmpty() {
		if (taskCount() == 0)
			return true;
		else
			return false;
	}

	/*
	 * Adds a new task to the list. The place in the list for the task is
	 * determined by the particular data structure of the class that implements
	 * the TaskList interface.
	 * 
	 * @see TaskList#addTask(Task)
	 */
	public void addTask(Task task) {
		//check this
		if (taskNumber >= priorityArray.length) {
			Task[] temp = new Task[2 * priorityArray.length];
			for (int k = 0; k < priorityArray.length; k++) {
				temp[k] = priorityArray[k];
			}
			priorityArray = temp;
		}

		if (task.deadline() == null) {
			Task[] temp1 = new Task[priorityArray.length];
			temp1 = priorityArray;
			for (int i = taskNumber; i >= 0; i--) {
				priorityArray[i + 1] = temp1[i];
			}
			priorityArray[0] = task;
			taskNumber++;
		}

		else if (taskNumber == 0) {
			priorityArray[taskNumber] = task;
			taskNumber++;
		}

		else {

			boolean check = true;
			for (int k = 0; k < taskNumber && priorityArray[k]!=null; k++) {
				if (check == true
						//This is were the exception for th3e loading is coming up
						&& priorityArray[k].deadline() != null
						&& task.deadline().compareTo(
								priorityArray[k].deadline()) == TaskUtility.SAME_DATE
						&& task.deadline().compareTo(
								priorityArray[k + 1].deadline()) != TaskUtility.SAME_DATE) {

					for (int t = taskNumber; t >= k + 1; t--) {
						priorityArray[t] = priorityArray[t - 1];
					}
					priorityArray[k + 1] = task;
					check = false;
				}

				else if (check == true
						&& priorityArray[k].deadline() != null
						&& task.deadline().compareTo(
								priorityArray[k].deadline()) == TaskUtility.LATER_DATE) {

					for (int t = taskNumber; t > k; t--) {
						priorityArray[t] = priorityArray[t - 1];
					}
					priorityArray[k] = task;
					check = false;
				}

			}
			taskNumber++;
		}

	}

	/*
	 * Removes the next task from the list and returns it. If the list is empty,
	 * returns null.
	 * 
	 * @see TaskList#removeNextTask()
	 */
	public Task removeNextTask() {
		if (priorityArray[0] == null)
			return null;
		Task tempPriority = priorityArray[taskNumber - 1];
		priorityArray[taskNumber - 1] = null;

		taskNumber--;
		return tempPriority;

	}

	/*
	 * Returns a new array that contains all of the tasks in the list. The array
	 * should be arranged so that the elements are in the order they would be
	 * returned by successive calls to removeNextTask(), from first to last.
	 * 
	 * @see TaskList#allTasks()
	 */
	public Task[] allTasks() {

		Task[] tempPriorityArray = new Task[taskNumber];

		for (int k = 0; k < taskNumber; k++) {
			tempPriorityArray[k] = priorityArray[k];
		}

		return tempPriorityArray;
	}

	/*
	 * Returns the next task from the list. Does not remove the task from the
	 * list. If the list is empty, returns null.
	 * 
	 * @see TaskList#nextTask()
	 */
	public Task nextTask() {
		if (priorityArray[0] == null)
			return null;
		return priorityArray[taskNumber - 1];
	}

	/*
	 * Initializes the task list with the tasks in the array. Any current
	 * contents of the list should be erased and completely overwritten with the
	 * new tasks. The array can be assumed to have been created by the same type
	 * of data structure; however, if the class is given an array from another
	 * data structure, it cannot allow any of its ordering requirements to be
	 * violated (e.g., a TaskPrioritySequence that is initialized with an
	 * out-of-order array must reorder it properly).
	 * 
	 * @see TaskList#initWithTasks(Task[])
	 */
	public void initWithTasks(Task[] newTasks) {
		Task[] newTasksCopy = new Task[newTasks.length];
		priorityArray = newTasksCopy;
		taskNumber = 0;
		for (int k = 0; k < newTasks.length; k++) {
			addTask(newTasks[k]);
		}

	}

}
