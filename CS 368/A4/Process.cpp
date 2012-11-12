#include <iostream>
#include "Process.h"

using namespace std;


//Constructor
// Constructs a default Process object.
Process::Process ()
{
  PID = 0;
  CPUTime = 0;
}

//Constructor
// Constructs a default Process object.
Process::Process (int ProcID)
{
  PID = ProcID;
  CPUTime = 0;
}

//Copy Constructor
//Copies the contents of older object to the new object
Process::Process (const Process & Old_Process)
{
  PID = Old_Process.PID;
  CPUTime = Old_Process.CPUTime;
}


// getPID
// returns the student ID
int
Process::getPID () const 
{
  return PID;
}


// getCPUTime
// returns the number of credits
int
Process::getCPUTime () const 
{
  return CPUTime;
}


// addCPUTime
// Adds the time specified to the current CPUTime
void
Process::addCPUTime (int time)
{
  CPUTime += time;
}


// print
// Prints out the Process info to standard output in the format:
//   ID, CPUTime
// Note: the end-of-line is NOT printed
void
Process::print () const 
{
  cout << PID << ", " << CPUTime;
}
