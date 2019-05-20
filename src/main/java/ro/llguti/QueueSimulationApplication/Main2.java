/**
 * 
 */
package ro.llguti.QueueSimulationApplication;

import java.util.List;
import java.util.Scanner;


/**
 * @author LG
 *
 */
public class Main2{

	/**
	 * @param args
	 */

	public static void main2(String[] args) {
		// TODO Auto-generated method stub
		int nrOfQueues = 3;
		int simulationTime = 3000;
		int minInt = 100;
		int maxInt = 300;
		int minServ = 75;
		int maxServ = 160;
		
		//Scanner input = new Scanner(System.in);
		//System.out.println("Nr. of queues: ");
		//nrOfQueues = input.nextInt();
		
		//System.out.println("Simulation interval: ");
		//simulationTime = input.nextInt();
				
		//System.out.println("Minimum interval of client generation: ");
		//minInt = input.nextInt();
		//System.out.println("Maximum interval of client generation: ");
		//maxInt = input.nextInt();
		
		//System.out.println("Minimum service time: ");
		//minServ = input.nextInt();
		//System.out.println("Maximum service time: ");
		//maxServ = input.nextInt();
		
		long arrivalTime = System.currentTimeMillis();
		long closingTime = arrivalTime + 1000 * simulationTime;
		List<Queue> qs = QueueUtil.create(nrOfQueues, closingTime);
		QueueUtil.startQueues(qs);
		
		ClientManager cm = new ClientManager(qs, arrivalTime, closingTime, minInt, maxInt, minServ, maxServ);
		cm.run();
		try {
			Thread.sleep(simulationTime * 1000 + 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Average waiting time: " + cm.getAvgWaitingTime());
		System.out.println("Maximum waiting time: " + cm.getMaxWaitingTime());
		System.out.println("Total waiting time: " + cm.getTotalWaitingTime());
		QueueUtil.rushHour(qs);
		
		/*queues=3
		 * simtime=120
		 * min int=20
		 * max int = 50 
		 * min serv t = 15
		 * max serv t 30
		 */
	}
	
}
