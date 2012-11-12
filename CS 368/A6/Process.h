#ifndef PROCESS_H
#define PROCESS_H

/*
 * Process class
 *
 * Just a small amount of the info that Process control block within
 * an OS would have.
 */
class Process {

  public:
    
    Process();
    // Constructs a default Process object.

    Process(const Process & );
    //Copy constructor

    Process(int PID);
    // Constructs a Process with the given PID, ???.

	~Process();

    int getPID() const;      // returns the PID

    int getCPUTime() const;  // returns the CPUTime

    void addCPUTime(int time);  
    // Adds the time specified to the current CPUTime

    void print() const;  
    // Prints out the Process info to standard output in the format:
    //   PID, CPUTime
    // Note: the end-of-line is NOT printed

//  private:
    int PID;
    int CPUTime;   // accumulated CPU time, in microseconds
};

#endif
