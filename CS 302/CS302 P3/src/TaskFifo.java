public class TaskFifo implements TaskList {

	private Task[] fifoArray;
	private int taskNumber;

	// Constructor
	public TaskFifo() {
		fifoArray = new Task[20];
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
		if (taskNumber >= fifoArray.length) {
			Task[] temp = new Task[2 * fifoArray.length];
			for (int k = 0; k < fifoArray.length; k++) {
				temp[k] = fifoArray[k];
			}
			fifoArray = temp;
		}
		Task[] temp1 = new Task[fifoArray.length];
		temp1 = fifoArray;
		for (int i = taskNumber; i >= 0; i--) {
		
			fifoArray[i + 1] = temp1[i];
		}
		fifoArray[0] = task;
		taskNumber++;
	}

	/*
	 * Returns the next task from the list. Does not remove the task from the
	 * list. If the list is empty, returns null.
	 * 
	 * @see TaskList#nextTask()
	 */
	public Task nextTask() {
		if (fifoArray[0] == null)
			return null;
		return fifoArray[taskNumber - 1];
	}

	public Task removeNextTask() {
		if (fifoArray[0] == null)
			return null;
		Task tempFifo = fifoArray[taskNumber - 1];
		fifoArray[taskNumber - 1] = null;

		taskNumber--;
		return tempFifo;
	}

	/*
	 * Returns a new array that contains all of the tasks in the list. The array
	 * should be arranged so that the elements are in the order they would be
	 * returned by successive calls to removeNextTask(), from first to last.
	 * 
	 * @see TaskList#allTasks()
	 */
	public Task[] allTasks() {

		Task[] tempFifoArray = new Task[taskNumber];

		for (int k = 0; k < taskNumber; k++) {
			tempFifoArray[k] = fifoArray[k];
		}

		return tempFifoArray;
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
		fifoArray = newTasksCopy;
		taskNumber = 0;
		for (int k = 0; k < newTasks.length; k++) {
			addTask(newTasks[k]);
		}
	}

}
