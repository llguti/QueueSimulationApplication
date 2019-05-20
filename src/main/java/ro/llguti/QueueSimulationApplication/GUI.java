/**
 * 
 */
package ro.llguti.QueueSimulationApplication;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author LG
 *
 */
public class GUI extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 680;
	private final int HEIGHT = 700;
	
	private JPanel panel = new JPanel();
	
	private JLabel nrOfQueuesLB = new JLabel("Number of queues: ");
	private JLabel simulationTimeLB = new JLabel("Simulation interval: ");
	private JLabel minIntLB = new JLabel("Minimum interval of client generation: ");
	private JLabel maxIntLB = new JLabel("Maximum interval of client generation: ");
	private JLabel minServLB = new JLabel("Minimum service time: ");
	private JLabel maxServLB = new JLabel("Maximum service time: ");
	private JLabel output = new JLabel("Output: ");
	
	private JTextField nrOfQueuesTF = new JTextField("3");
	private JTextField simulationTimeTF = new JTextField("110");
	private JTextField minIntTF = new JTextField("20");
	private JTextField maxIntTF = new JTextField("50");
	private JTextField minServTF = new JTextField("15");
	private JTextField maxServTF = new JTextField("30");
	public static JTextArea log = new JTextArea();
	private JScrollPane jsp = new JScrollPane(log);
	private JButton startB = new JButton("Start");
	//private JButton getDataB = new JButton("Get Data");
	
	private int nrOfQueues;
	private int simulationTime;
	private int minInt;
	private int maxInt;
	private int minServ;
	private int maxServ;
	private long arrivalTime;
	private long closingTime;	
	
    public void addWidgets() {

    	nrOfQueuesLB.setBounds(10, 10, 290, 20);
    	simulationTimeLB.setBounds(10, 40, 290, 20);
    	minIntLB.setBounds(10, 70, 290, 20);
    	maxIntLB.setBounds(10, 100, 290, 20);
    	minServLB.setBounds(10, 130, 290, 20);
    	maxServLB.setBounds(10, 160, 290, 20);
    	output.setBounds(10, 190, 100, 20);
    	startB.setBounds(400, 90, 100, 20);
    	//getDataB.setBounds(400, 120, 100, 20);
    	
    	nrOfQueuesTF.setBounds(291, 10, 50, 20);
    	simulationTimeTF.setBounds(291, 40, 50, 20);
    	minIntTF.setBounds(291, 70, 50, 20);
    	maxIntTF.setBounds(291, 100, 50, 20);
    	minServTF.setBounds(291, 130, 50, 20);
    	maxServTF.setBounds(291, 160, 50, 20);
    	jsp.setBounds(70, 190, 550, 430);
    	jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	panel.add(nrOfQueuesLB);
		panel.add(simulationTimeLB);
		panel.add(minIntLB);
		panel.add(maxIntLB);
		panel.add(minServLB);
		panel.add(maxServLB);
		panel.add(output);
		panel.add(startB);
		startB.addActionListener(this);
		//panel.add(getDataB);
		panel.add(nrOfQueuesTF);
		panel.add(simulationTimeTF);
		panel.add(minIntTF);
		panel.add(maxIntTF);
		panel.add(minServTF);
		panel.add(maxServTF);
		//add scroll to the panel
		panel.add(jsp);
    }
	
	private void framePosition() {
    	setSize(WIDTH, HEIGHT);		
    	setVisible(true);
    	setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Queue Evolution Simulator");
		
	}
	
	public GUI() {
    	add(panel);
    	panel.setLayout(null);
    	addWidgets();
    	framePosition();
    }
	
	public int getNrOfQueues() {
		return Integer.parseInt(nrOfQueuesTF.getText());
	}
	
	public int getSimulationTime() {
		return Integer.parseInt(simulationTimeTF.getText());
	}
	
	public int getMinInt() {
		return Integer.parseInt(minIntTF.getText());
	}
	
	public int getMaxInt() {
		return Integer.parseInt(maxIntTF.getText());
	}
	
	public int getMinServ() {
		return Integer.parseInt(minServTF.getText());
	}
	
	public int getMaxServ() {
		return Integer.parseInt(maxServTF.getText());
	}
	
	public void addStartBtn(ActionListener a) {
		startB.addActionListener(a);
	}
	
	//public void addGetDataBtn(ActionListener a) {
	//	getDataB.addActionListener(a);
	//}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton testButton = (JButton) e.getSource();
		if(testButton == startB) {
			//generate queues
			//log.setText("test\n");
			//String queNr = nrOfQueuesTF.getText();
			nrOfQueues = getNrOfQueues();
			//log.append(queNr);
			//String simTime = simulationTimeTF.getText();
			simulationTime = getSimulationTime();
			//String minimInt = minIntTF.getText(); 
			minInt = getMinInt();
			//String maximInt = maxIntTF.getText();
			maxInt = getMaxInt();
			//String minimServ = minServTF.getText();
			minServ = getMinServ();
			//String maximServ = maxServTF.getText();
			maxServ = getMaxServ();
			arrivalTime = System.currentTimeMillis();
			closingTime = arrivalTime + 1000 * simulationTime;
			List<Queue> qs = QueueUtil.create(nrOfQueues, closingTime);
			QueueUtil.startQueues(qs);
			ClientManager cm = new ClientManager(qs, arrivalTime, closingTime, minInt, maxInt, minServ, maxServ);
			cm.run();
			try {
				Thread.sleep(simulationTime * 1000 + 1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.append("Average waiting time: " + cm.getAvgWaitingTime() + "\n");
			log.append("Maximum waiting time: " + cm.getMaxWaitingTime() + "\n");
			log.append("Total waiting time: " + cm.getTotalWaitingTime() + "\n");
			QueueUtil.rushHour(qs);
		}
	}
		/*
		 * queues=3
		 * simtime=110
		 * min int=20
		 * max int = 50 
		 * min serv t = 15
		 * max serv t 30
		 */
}	