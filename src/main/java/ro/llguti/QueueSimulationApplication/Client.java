/**
 * 
 */
package ro.llguti.QueueSimulationApplication;

/**
 * @author LG
 *
 */
public class Client {

		private int serviceTime;
		private String clientName;
		private int waitingTime;
			
		public Client(String clientName) {
			this.serviceTime = 0;
			this.clientName = clientName;
		}
			
		public Client(int serviceTime, String clientName) {
			this.serviceTime = serviceTime;
			this.clientName = clientName;
		}
		
		public int getWaitingTime() {
			return waitingTime;
		}
		
		public void setWaitingTime(int waitingTime) {
			this.waitingTime = waitingTime;
		}
	
		public String getClientName() {
			return this.clientName;
		}
			
		public int getServiceTime() {
			return serviceTime;
		}
			
		
}


