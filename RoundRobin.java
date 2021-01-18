package schedulers;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RoundRobin {
	static process [] processes ;
	 static boolean allDone (process [] processes) {		// checks for all processes execution completion
		 boolean check = true ;
		 for(int i =0 ; i<processes.length ;i++) {
			 if(processes[i].getBT() != 0) {
				 check = false ;	// still need to be executed
				 break;
			 }
		 }
		 return check;
	 }
	 static void RR(process [] processes , int tq , int CTswitch) {
		int time = 0 ;
		process currentContext = null;
		Queue <process> request = new LinkedList <process> ();
		
		// --------------Processes Execution Order--------------
		System.out.println("-------------- Processes Execution Order --------------");
		System.out.println(" Process name	  |    From    |	To");
		System.out.println("-------------------------------------------");
		while( ! allDone(processes)) {	// CPU processes execution order
			for(int i = 0 ; i<processes.length ; i++) {		// check for waiting processes
				if(processes[i].getAT() <= time && processes[i].getStatus()) {
					processes[i].setwaitTime(time - processes[i].getAT()); 	// waiting time before execution
					request.add(processes[i]) ;
					processes[i].setStatus(false);
				}
			}
			if(currentContext != null) {
				for(int i =0 ; i<processes.length ; i++) {
					if( processes[i].getname().equals(currentContext.getname()) ){
						request.add(processes[i]);
						break;
					}
				}
				currentContext = null;
			}
			if(! request.isEmpty()) {
				System.out.print("\t" + request.peek().getname() + "\t|\t"+time+"\t|");
	
				
				
				if(request.peek().getBT() > tq) {
					time += tq;
					currentContext = request.peek();
					request.peek().setBT( request.peek().getBT()-tq );
					request.peek().trdInc(CTswitch); 		// adding context switching time to the process turnarround time
					request.poll();
					for(int i =0 ; i<processes.length ;i++) {
						if(request.contains(processes[i])) {
							processes[i].setwaitTime(  processes[i].getwaitTime()+tq+CTswitch); 	// increase waiting time
						}
					}
					time = time + CTswitch ;			// adding context switching time to the clock ( time )
					System.out.println("\t" + time);		// interruption time
					continue;
				}else {
					int execTime = request.peek().getBT();
					time += request.peek().getBT();
					request.peek().setBT(0);
					request.peek().trdInc(CTswitch);
					request.poll();
					for(int i =0 ; i<processes.length ;i++) {
						if(request.contains(processes[i])) {
							processes[i].setwaitTime(  processes[i].getwaitTime()+ execTime + CTswitch); 	// increase waiting time
						}
					}
					time = time + CTswitch ;			// adding context switching time to the clock ( time )
					System.out.println("\t" + time);
					continue;
				}
			}else {
				time = time +1 ;
			}
			 
		}
		// -------------- Processes Analysis --------------
		System.out.println("-------------- Processes Analysis --------------");
		System.out.println("Process name   |    waiting time     |    Turnaround time");
		System.out.println("----------------------------------------------------------");
		int totalwait = 0 ;
		int totaltrd = 0 ;
		for(int i =0 ; i<processes.length ;i++) {
			processes[i].settrd();
			totalwait = totalwait + processes[i].getwaitTime();
			totaltrd = totaltrd + processes[i].gettrd() ;
			System.out.print("\t"+processes[i].getname());
			System.out.print("\t|\t"+processes[i].getwaitTime());
			System.out.println("\t|\t\t" + processes[i].gettrd());
		}
		System.out.println();
		System.out.println("Average waiting time : " + (totalwait/processes.length));
		System.out.println("Average Turnaround time: " + (totaltrd/processes.length));
		
	}
	
}
