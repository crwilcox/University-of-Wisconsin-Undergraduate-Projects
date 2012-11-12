public class TaskStack implements TaskList {
	private Task[] stackArray;
	private int taskNumber;

	public TaskStack() {
		stackArray = new Task[20];
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
	 * adds a task to the top of the stack
	 * 
	 * @see TaskList#addTask(Task)
	 */
	public void addTask(Task task) {
		if (taskNumber >= stackArray.length) {
			Task[] temp = new Task[2 * stackArray.length];
			for (int k = 0; k < stackArray.length; k++) {
				temp[k] = stackArray[k];
			}
			stackArray = temp;
		}
		stackArray[taskNumber] = task;
		taskNumber++;
	}

	/*
	 * Returns the next task from the list. Does not remove the task from the
	 * list. If the list is empty, returns null. returns the
	 * 
	 * @see TaskList#nextTask()
	 */
	public Task nextTask() {
		if (stackArray[0] == null)
			return null;
		// What is defined as the next task
		return stackArray[taskNumber - 1];
	}

	/*
	 * Removes the next task from the list and returns it. If the list is empty,
	 * returns null.
	 * 
	 * @see TaskList#removeNextTask()
	 */
	public Task removeNextTask() {
		// ask if this is correct
		if (stackArray[0] == null)
			return null;

		Task tempStack = stackArray[taskNumber - 1];
		stackArray[taskNumber - 1] = null;
		taskNumber--;
		return tempStack;

	}

	/*
	 * Returns a new array that contains all of the tasks in the list. The array
	 * should be arranged so that the elements are in the order they would be
	 * returned by successive calls to removeNextTask(), from first to last.
	 * 
	 * @see TaskList#allTasks()
	 */
	public Task[] allTasks() {
		Task[] tempStackArray = new Task[taskNumber];

		for (int k = 0; k < taskNumber; k++) {
			tempStackArray[k] = stackArray[k];
		}
		
		

		return tempStackArray;
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
		stackArray = newTasksCopy;
		taskNumber = 0;
		for (int k = 0; k < newTasks.length; k++) {
			addTask(newTasks[k]);
		}
	}

}
