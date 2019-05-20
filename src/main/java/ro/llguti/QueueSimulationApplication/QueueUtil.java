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
public class QueueUtil {
	
	private QueueUtil() {}
	
	public static List<Queue> create(int nrQueues, long closingTime) {
		//method to create Queues, returns a List of the created Queues
		List<Queue> qs = new ArrayList<Queue>();
		for(int i=0; i<nrQueues; i++) {
			Queue q = new Queue(closingTime);
			q.setQueueName(i + "");
			qs.add(q);
		}
		return qs;
	}
	
	public static void startQueues(List<Queue> qs) {
		//method for creating Threads for every Queue
		for (int i = 0; i < qs.size(); i++) {
			Thread t = new Thread(qs.get(i));
			t.start();
		}
	}
	
	public static void rushHour(List<Queue> qs) {
		for (int i = 0; i < qs.size(); i++) {
			String message = "Rush hour of queue nr." + qs.get(i).getQueueName() + " was at " + qs.get(i).getRushHour()/1000 + "\n";
			GUI.log.append(message);
		}
	}
	
	public static Queue findOptimalQueue(List<Queue> qs) {
		//the method is to decide which queue will be assigned to the next customer in line
		//returns the queue which has the lowest waiting time
		Queue q = qs.get(0);
		for(Queue queue : qs) {
			if(queue.getWaitingTime() < q.getWaitingTime()) {
				q = queue;
			}
		}
		return q;
	}
}
