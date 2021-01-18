package schedulers;

import java.util.Scanner;

import java.util.Vector;

public class MultiLevel {
	int tq;
	int time=0;
	int time1=0;
	int contextSwitch = 0;
	RoundRobin R;
	process[] all;
	Vector<process> allproc=new<process> Vector() ;

	Vector<process> RR_q=new<process> Vector()  ;
	Vector<process> pree_q=new<process> Vector() ;
	int[] que;
	int x=0;
	int y=0;	
	
	Scanner scan=new Scanner(System.in);
	public int getTq() {
		return tq;
	}

	public void setTq(int tq) {
		this.tq = tq;
	}

	public void setcs(int contextSwitch) {
		this.contextSwitch = contextSwitch;
	}

	
	public process[] getAll() {
		return all;
	}

	public void setAll(process[] proc) {
		this.all = proc;
		
	}

	

	
	
	
	void takeprocess( process[] all)
	{   
		que=new int[all.length];
		


		for(int i=0;i<all.length;i++)
		{
			System.out.println("enter the queue number that "+all[i].name +" will enter"
					+ "");
			allproc.add(all[i]);
			que[i]=scan.nextInt();
		}
		int i=0;
	    int q=0;
        while(!allproc.isEmpty())
        {
					
		if (que[q]==1)
		{ 
			RR_q.add(all[q]);
			allproc.remove(i);
			i--;
		}
		else if(que[q]==2)
		{
			pree_q.add(all[q]);
			allproc.remove(i);
			i--;

		}
		i++;
		q++;
        }
	}
	
	void excecution()
	{ 
		takeprocess(all);
		int x=0;
		Vector<process> exec = new<process> Vector() ;
		while(!RR_q.isEmpty() || !pree_q.isEmpty()) {

		for(int i=0;i<RR_q.size();i++)
		{
			if(RR_q.get(i).AT<=time)
			{
				exec.add(RR_q.get(i));
				time+=RR_q.get(i).BT;

				RR_q.remove(i);
				i--;
			}
			 
		}
		process[] workdone=new process[exec.size()];
		for(int j=0;j<exec.size();j++)
			workdone[j]=exec.get(j);
			
	   R.RR(workdone, tq,contextSwitch);
	   exec.clear();
	   	
	        if(!RR_q.isEmpty())
	        {
	   			if(time<RR_q.get(x).AT && pree_q.get(0).AT<=time)
			{
				System.out.println(" process name: "+pree_q.get(0).name+"  process brust time: "+pree_q.get(0).BT+" runing using FCFS\n");
			   
				try {
					System.out.println("WAIT.......");
					Thread.sleep((RR_q.get(x).AT-time)*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			    pree_q.get(0).BT-=(RR_q.get(x).AT-time);
			    
				if(pree_q.get(0).BT==0) {
					System.out.println(" process name: "+ pree_q.get(0).name +"  process completed after : "+(RR_q.get(x).AT-time)+" seconds\n");
					pree_q.remove(0);

					
				}
				else
				System.out.println(" process name: "+ pree_q.get(0).name +"  process interrupted after : "+(RR_q.get(x).AT-time)+" seconds\n");	
				
				time+=(RR_q.get(x).AT-time);
				
				
				
			}
	   			else 
	   			{
	   				if (pree_q.get(0).AT<RR_q.get(x).AT)
	   					time=pree_q.get(0).AT;
	   				else
	   					time=RR_q.get(x).AT;
	   			}
	   			
	        }
	        else 
	        {
	        	for(int i=0;i<pree_q.size();i++)
	        	{
					System.out.println(" process name: "+pree_q.get(i).name+"  process brust time: "+pree_q.get(i).BT+" runing using FCFS\n");
					try {
						System.out.println("WAIT.......");
						Thread.sleep((pree_q.get(i).BT)*1000);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	
				System.out.println(" process name: "+ pree_q.get(i).name +"  process completed after : "+pree_q.get(i).BT+" seconds\n");

	        	}
	        	break;
	        }
	   
	   
		}
	}
}
