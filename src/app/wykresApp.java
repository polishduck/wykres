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
    		commConnector.connect("/dev/ttyACM0", 115200, 8, 1, 0);
    	} catch(Exception e) {
     		//System.out.println(e.getMessage());
    		return;
    	}
    	
    	SimpleCommListener listener =  commConnector.new SimpleCommListener();
    	listener.myView = myView;
    	commConnector.addListener(listener);
    	
    	try {
			commConnector.startPortListening();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			return;
		}
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