/**
 * 
 */
package ro.llguti.QueueSimulationApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LG
 *
 */
public class Queue implements Runnable{
	private List<Client> clients;
	private String queueName;
	private long arrivalTime;
	private long closingTime;
	private int waitingTime;
	private long rushHour;
	private int maxClients;
	
	public Queue(long closingTime) {
		clients = new ArrayList<Client>();
		this.closingTime = closingTime;
		this.arrivalTime = System.currentTimeMillis();
	}
	
	public String getQueueName() {
		return this.queueName;
	}
	
	public void setQueueName(String name) {
		this.queueName = name;
	}
	
	public int getWaitingTime() {
		return waitingTime;
	}
	
	public long getRushHour() {
		return rushHour;
	}
	/*
	 * adds a client to the queue
	 */
	public void addClient(Client client) {
		waitingTime += client.getServiceTime();		//service time is added to the waiting time of the client
		client.setWaitingTime(waitingTime);		//waiting time of the client is set in function of the waiting time of the queue
		clients.add(client);	//client is added to the queue
		if (clients.size() > maxClients) {
			maxClients = clients.size();
			rushHour = (closingTime - arrivalTime) - (closingTime - System.currentTimeMillis());
		}
	}
	
	public Client getClient(int index) {
		return clients.get(index);
	}
	
	public int getIndexOf(Client client) {
		
		return clients.indexOf(client);
	}
	
	public int getClientSize() {
		return clients.size();
	}

	public Client pop() {
		if(clients.size() > 0) {	//if the queue is not empty the first client is extracted and removed from the queue
			Client client = clients.get(0);
			clients.remove(0);
			waitingTime -= client.getServiceTime();		//service time of the client is subtracted from the waiting time of the queue
			return client;
		}
		return null;
	}
	
	@Override
	public void run() {
		Client client = this.pop();		//a client is extracted form the queue
		float currentTime = System.currentTimeMillis();		//current time is saved
		while(client != null || currentTime < closingTime) {	//(client != null || currentTime > closingTime)
			if (client != null) {	//if the queue is not empty or closed the the client gets processed
				try {	
					//System.out.println("Queue nr." + this.getQueueName() + " processing client nr." + client.getClientName());
					String message = "Queue nr." + this.getQueueName() + " processing client nr." + client.getClientName() +"\n";	
					GUI.log.append(message);
						Thread.sleep(client.getServiceTime() * 1000);	//thread to pause for 1 seconds after client gets displayed
				} catch (InterruptedException e) {
				}
			}
			currentTime = System.currentTimeMillis();
			client = this.pop();
		}
	}
}