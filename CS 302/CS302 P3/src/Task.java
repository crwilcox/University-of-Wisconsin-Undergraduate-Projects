public class Task {
	private SimpleDate deadline;
	public String description;

	public Task(java.lang.String description) {
		this.description=description;
		deadline = null;
	}

	public Task(java.lang.String description, SimpleDate deadline) {
		this.description = description;
		this.deadline = deadline;
	}

	public SimpleDate deadline() {
		return deadline;
	}

	public java.lang.String description() {
		return description;
	}

	public java.lang.String toString() {
		String task="";
		task += this.description;
		if (deadline == null)
			task += " (no deadline)";
		else
			task += " ("+deadline+")";
		
		return task;
	}
}
