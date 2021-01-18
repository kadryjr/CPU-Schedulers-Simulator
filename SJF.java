package schedulers;

import java.util.Scanner;

class Processx{
    String processName;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnAroundTime;

    public Processx (String processName, int arrivalTime, int burstTime){
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        waitingTime = 0;
        turnAroundTime = 0;
    }
}



public class SJF {
    private int numberOfProcesses;
    private final Processx[] processes = new Processx[20];
    private int contextSwitch;

    public SJF() {
        numberOfProcesses = -1;
        contextSwitch = -1;

    }

    public void setNumberOfProcesses(int numberOfProcesses, int contextSwitch) {
        this.numberOfProcesses = numberOfProcesses;
        this.contextSwitch = contextSwitch;
    }

    public void setProcessesInfo() {
        Processx process;
        String name;
        int arrival, burst;
        Scanner scanInt = new Scanner(System.in);
        Scanner scanStr = new Scanner(System.in);
        for (int i = 0; i < numberOfProcesses; i++){
            System.out.println("Process " + (i + 1));

            System.out.print("Enter Process name: ");
            name = scanStr.nextLine();


            System.out.print("Enter Arrival Time: ");
            arrival = scanInt.nextInt();

            System.out.print("Enter Burst Time: ");
            burst = scanInt.nextInt();

            process = new Processx(name, arrival, burst);

            processes[i] = process;
        }
    }

    private void burstIncreasing(){
        for (int i = 0; i < numberOfProcesses; i++)
            processes[i].burstTime += contextSwitch;
    }

    private void calculateWaitingTime() {
        int[] burstTemp = new int[numberOfProcesses];

        // Copy the burst time into burstTemp[]
        for (int i = 0; i < numberOfProcesses; i++)
            burstTemp[i] = processes[i].burstTime;

        int complete = 0, aT = 0, minimum = 999999999, shortest = 0, finishTime, from = 0, to = 0, counter = 0;
        boolean check = false, isFirstTime = true, isFirst = true;
        String prev = "";
        /*
        complete: number of completed processes
        aT: arrival time of each process
        minimum: to get the minimum burst time
        shortest: the index number of the process that has the minimum burst time
        finishTime: the time when the process completed
        from: the execution of the process time from time ...
        to: the execution time of the process to time ...
        check: flag to check if there is a process at this arrival time
        isFirstTime: to check that its the first time to go in loop
        temp: to get the name of the process in execution
         */

        System.out.println("Processes execution order: \n" +
                "Process name" + "                                       " + "In execution");

        while (complete != numberOfProcesses) {

            // To get an arrived process with the minimum burst (execution) time
            for (int i = 0; i < numberOfProcesses; i++) {
                if ((processes[i].arrivalTime <= aT) && (burstTemp[i] < minimum) && (burstTemp[i] > 0)) {
                    minimum = burstTemp[i];
                    shortest = i;
                    if(isFirstTime) {
                        prev = processes[shortest].processName;
                        from = aT;
                        isFirstTime = false;
                    }
                    check = true;
                }
            }

            if (!check) {
                aT++;
                continue;
            }
            if(!prev.equalsIgnoreCase(processes[shortest].processName)){
                if (isFirst){
                    to = aT;
                    isFirst = false;
                } else
                    to = from + counter;
                for(int i = 0; i < numberOfProcesses; i++){
                    if(processes[i].processName.equalsIgnoreCase(prev)){
                        System.out.println(processes[i].processName +
                                "                                               " +
                                "From: " + from + "     " +
                                "To: " + to);
                    }
                }
                prev = processes[shortest].processName;
                from = to + contextSwitch;
                counter = 1;
            }else
                counter++;


            burstTemp[shortest]--;

            minimum = burstTemp[shortest];

            if (minimum == 0)
                minimum = 999999999;


            if (burstTemp[shortest] == 0) {

                complete++;
                check = false;

                finishTime = to + contextSwitch + 1;

                processes[shortest].waitingTime = finishTime - processes[shortest].burstTime - processes[shortest].arrivalTime;

                if (processes[shortest].waitingTime < 0)
                    processes[shortest].waitingTime = 0;
            }
            aT++;
        }
        to = from + counter;
        System.out.println(processes[shortest].processName +
                "                                               " +
                "From: " + from + "     " +
                "To: " + to);
    }

    private void calculateTurnAroundTime() {
        for (int i = 0; i < numberOfProcesses; i++)
            processes[i].turnAroundTime = processes[i].burstTime + processes[i].waitingTime;
    }

    private double averageWaitingTime(){
        double awt = 0;
        for (int i = 0; i < numberOfProcesses; i++)
            awt += processes[i].waitingTime;
        awt /= numberOfProcesses;
        return awt;
    }

    private double averageTurnAroundTime(){
        double tat = 0;
        for (int i = 0; i < numberOfProcesses; i++)
            tat += processes[i].turnAroundTime;
        tat /= numberOfProcesses;
        return tat;
    }


    public void printing(){
        calculateWaitingTime();
        calculateTurnAroundTime();
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println("n" + "              " +
                "Process name" + "              " +
                "Waiting time" + "              " +
                "Turn around time" + "              ");
        for(int i = 0; i < numberOfProcesses; i++){
            System.out.println((i+1) +
                    "                   " + processes[i].processName +
                    "                         " + processes[i].waitingTime +
                    "                           " + processes[i].turnAroundTime);
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        double awt = averageWaitingTime();
        System.out.println("Average Waiting Time = " + awt);
        double tat = averageTurnAroundTime();
        System.out.println("Average Turn Around Time = " + tat);
    }
}