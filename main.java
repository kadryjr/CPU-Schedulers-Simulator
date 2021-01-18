package schedulers;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class INFo implements Comparable<INFo>{


	String name;
	public Integer priority,burstTime, arrivalTime, waitingTime;
	INFo(String Name, Integer BurstTime, Integer ArrivalTime, Integer Priority ){
		name = Name;
		priority = Priority;
		burstTime = BurstTime;
		arrivalTime = ArrivalTime;
		waitingTime = 0;
	}


	public int compareTo(INFo process) {
		int compareArrival = process.arrivalTime;
		return this.arrivalTime - compareArrival;
	}

}


public class main {
	public static void priority (INFo InformationArray[], int Numberofprocess){


		for (int i = 1; i < Numberofprocess; i++) {
			int temp = InformationArray[i].arrivalTime;
			for (int j = i; j > 0 && temp < InformationArray[j - 1].arrivalTime; j--) {
				//SWAP 2 objects
				INFo tempp = InformationArray[j];
				InformationArray[j] = InformationArray[j - 1];
				InformationArray[j - 1] = tempp;

			}
		}
		int[] TurnArroundTime = new int[Numberofprocess];
		for (int i = 0; i < Numberofprocess; i++) {
			TurnArroundTime[i] = InformationArray[i].burstTime;
		}

		int Time = 0;
		int x = 0;
		ArrayList<INFo> ArrayInfo = new ArrayList<>();

		while (!ArrayInfo.isEmpty() || x < Numberofprocess) {
			if (x < Numberofprocess) {
				for (int i = x; i < Numberofprocess; i++) {
					if (InformationArray[x].arrivalTime <= Time) {

						ArrayInfo.add(InformationArray[i]);
						x++;

					}
				}
			}

			Collections.sort(ArrayInfo, (INFo obj1, INFo obj2) -> {
				if (obj1.priority < obj2.priority) {
					return -1;
				}
				if (obj1.priority > obj2.priority) {
					return 1;
				}
				return 0;
			});
			if (!ArrayInfo.isEmpty()) {
				ArrayInfo.get(0).burstTime--;

				System.out.println(ArrayInfo.get(0).name+"             in the Second:" + Time  );

			} else {
				System.out.println("NoProcessToDo       "+"in the Second:"+Time);

			}


			Time++;

			for (int i = 1; i < ArrayInfo.size(); i++) {
				ArrayInfo.get(i).waitingTime++;
				ArrayInfo.get(i).priority--;
			}

			if (!ArrayInfo.isEmpty()) {
				if (ArrayInfo.get(0).burstTime == 0) {
					ArrayInfo.remove(0);
				}

			}
		}
		System.out.println("_______________________________________________________");
		System.out.println("Process Name         waiting time turn         Turn Arround Time  ");
		double TotalWaitingTime = 0;
		double TotalAroundTime = 0;
		for (int i = 0; i < Numberofprocess; i++) {
			TurnArroundTime[i] += InformationArray[i].waitingTime;

			System.out.println(InformationArray[i].name + "                        " + InformationArray[i].waitingTime+"                          "+ TurnArroundTime[i] );

			TotalWaitingTime += InformationArray[i].waitingTime;
			TotalAroundTime += TurnArroundTime[i];

		}
		System.out.println("_______________________________________________________");
		System.out.println("Average waiting time = " + (TotalWaitingTime / Numberofprocess) + " secs");
		System.out.println("Average around time = " + (TotalAroundTime / Numberofprocess) + " secs");

		//main


	}
	public static void print()
	{
		Scanner sc = new Scanner(System.in);
		String name;
		Integer priority, burstTime, arrivalTime, size;

		System.out.println("Enter Number Of Processes");
		size = sc.nextInt();
		INFo process[] = new INFo[size];


		for(int i=0; i<size; i++) {
			System.out.println("Enter Process"+(i+1)+" Name:");
			name = sc.next();

			System.out.println("Enter Burst Time:");
			burstTime = sc.nextInt();

			System.out.println("Enter Arrival Time:");
			arrivalTime = sc.nextInt();

			System.out.println("Enter priority: ");
			priority = sc.nextInt();

			process[i] = new INFo(name,burstTime,arrivalTime,priority);
		}
		System.out.println("Process Name    Processes execution order ");
		priority(process,size);

	}




	public static void main(String[] args) {

		SJF sjf = new SJF ();
		Scanner scan = new Scanner(System.in);
        MultiLevel multilevel=new MultiLevel();
        RoundRobin rr = new RoundRobin();
		int num;
		String name ;
		int at  = 0;
		int bt = 0;
		int tq = 0;
		int contextSwitch = 0;
		process[] processes = null;
		System.out.println("chose the way of scheduling\n 1-SJF\n 2-Round Robin \n 3-preemptive\n 4-Multilevel");
		int x=scan.nextInt();
		if(x==1) {
			System.out.print("Enter the number of processes: ");
	        int numberOfProcesses = scan.nextInt();
	        System.out.print("Enter the number of context switching: ");
	        int contSwitch = scan.nextInt();
	        sjf.setNumberOfProcesses(numberOfProcesses, contSwitch);
	        sjf.setProcessesInfo();
	        sjf.printing();
		}else {
			if(x==3) {
				print();
			}else {
				do {
					System.out.println(" Enter the number of processes : ");
					num = scan.nextInt();

					if(num <1) {
						System.out.println(" Please enter a valid number !! ");
					}else {
						processes = new process[num];
					}
				}while(num <1);			// avoid input error
				scan.nextLine();
				for(int i =0 ; i<num ; i++) {		// input process info
					System.out.println(" Name of process " + (i+1) + " : ");
					name = scan.nextLine();

					processes[i] = new process ();
					processes[i].setname(name);
					System.out.println(" arrival time of process " + (i+1) + " : ");
					at = scan.nextInt();

					processes[i].setAT(at);
					System.out.println(" burst time of process " + (i+1) + " : ");
					bt = scan.nextInt();

					processes[i].setBT(bt);
					scan.nextLine();
				}
				System.out.println(" time quantum  (for RR)  : ");
				tq = scan.nextInt();

				System.out.println(" Context switch time (for RR and SJF ): ");
				contextSwitch = scan.nextInt();
				
				if(x==4){
					multilevel.setcs(contextSwitch);
					multilevel.setAll(processes);
					multilevel.setTq(tq);
					multilevel.excecution();
				}else {
					if(x==2) {
						rr.RR(processes, tq, contextSwitch);
					}
				}
			}
		}
		

	}

}
