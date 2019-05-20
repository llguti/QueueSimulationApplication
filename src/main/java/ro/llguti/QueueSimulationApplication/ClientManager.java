/**
 * 
 */
package ro.llguti.QueueSimulationApplication;

import java.util.List;
import java.util.Random;

/**
 * @author LG
 *
 */
public class ClientManager implements Runnable {
	
	private int nrClients;
	private List<Queue> qs;
	private long arrivalTime;
	private long closingTime;
	private int minInt;
	private int maxInt;
	private int minServ;
	private int maxServ;
	private Random r;
	
	private float totalWaitingTime = 0;
	private float maxWaitingTime = 0;
	private float avgWaitingTime;
	
	public ClientManager(List<Queue> qs, long arrivalTime, long closingTime, int minInt, int maxInt, int minServ, int maxServ) {
		this.qs = qs;
		this.arrivalTime = arrivalTime;
		this.closingTime = closingTime;
		this.minInt = minInt;
		this.maxInt = maxInt;
		this.minServ = minServ;
		this.maxServ = maxServ;
		this.nrClients = 0;
		r = new Random();		
	}
	
	@Override
	public void run() {
		for (int i = 0; arrivalTime < closingTime; i++) {			
			//a new "i" client is generated with service time: minServ, maxServ			
			int j = r.nextInt() % maxServ;
			if (j < 0) j *= -1;
			if (j == 0) j = minServ;
			Client customer = new Client(j, i + "");
			this.nrClients++;			
			//finding the optimal queue to place the client
			Queue queue = QueueUtil.findOptimalQueue(qs);
			queue.addClient(customer);			
			//maximum waiting time of the client and the total waiting time is calculated
			if (customer.getWaitingTime() > maxWaitingTime)
				maxWaitingTime = customer.getWaitingTime();
			totalWaitingTime += customer.getWaitingTime();
			//System.out.println("Customer " + i + " have been added to queue nr." + queue.getQueueName());
			String message = "Customer " + i + " have been added to queue nr." + queue.getQueueName() + "\n";
			GUI.log.append(message);
			arrivalTime = System.currentTimeMillis();
			//a random number is generated between minInt and maxInt, and based on this number the next client is generated
			int k = r.nextInt() % maxInt;
			if (k < 0) k *= -1;
			if (k == 0) k = minInt;
			try {
				Thread.sleep(k*500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public float getMaxWaitingTime() {
		return maxWaitingTime;
	}
	
	public float getTotalWaitingTime() {
		return totalWaitingTime;
	}
	
	public float getAvgWaitingTime() {
		avgWaitingTime = totalWaitingTime / nrClients;
		return avgWaitingTime;
	}
}
