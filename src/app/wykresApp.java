package app;

import java.util.ArrayList;
import java.util.Scanner;

import app.controller.CommConnector;
import app.controller.CommConnector.SimpleCommListener;
import app.controller.mainController;
import app.view.mainView;

import jssc.SerialPort;
import jssc.SerialPortException;

public class wykresApp {

	//MAIN FUNCTION
	
	public static void main(String[] args) {
	//	mainController mController = new mainController();
		mainView myView = new mainView();
		CommConnector commConnector = new CommConnector();
		
		
    	int i = 1;
    	ArrayList<String> portNames = null;
    	Scanner in = new Scanner(System.in);
    	try {
/*    		portNames = commConnector.getPortsNames();
    		if(portNames.size()==0) {
    			System.out.println("No ports found");
    	//		return;
    		}
    		System.out.println("Select port:");
    		
    		for(String pName : portNames) {
    			System.out.println(i+") "+pName);
    			i++;
    		}
*/    	//	i = in.nextInt();
    		//commConnector.connect(portNames.get(i-1), 115200, 8, 1, 0);
    		commConnector.connect("/dev/ttyACM0", 115200, 8, 1, 0);
    	} catch(Exception e) {
    		//e.printStackTrace();
    		System.out.println("paczka:" + e.getMessage() + "\n");
    		//System.out.println(e.getMessage());
    		return;
    	}
    	
    	SimpleCommListener listener =  commConnector.new SimpleCommListener();
    	//listener.setView(mController.myView);
    	listener.myView = myView;
    	commConnector.addListener(listener);
    	
    	try {
			commConnector.startPortListening();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("paczka:" + e.getMessage() + "\n");
			return;
		}
		
    	
    //	System.out.println("Connected to: "+portNames.get(i-1));
    	in.nextLine();
    	String line;
    	while(true) {
    		line = in.nextLine();
    		System.out.println("sending->"+line);
    		try {
    			commConnector.writeToPort(line);
    		} catch (SerialPortException e) {
    			e.printStackTrace();
    		}
    	}
    }
	}

	

//http://jchart2d.sourceforge.net/usage.shtml