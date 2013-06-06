package app;

import java.util.ArrayList;
import java.util.Scanner;

import app.controller.CommConnector;
import app.controller.CommConnector.SimpleCommListener;
import app.view.mainView;

import jssc.SerialPortException;

public class wykresApp {

	//MAIN FUNCTION
	
	public static void main(String[] args) {
		mainView myView = new mainView();
		CommConnector commConnector = new CommConnector();
		
    	Scanner in = new Scanner(System.in);
    	try {
    		commConnector.connect("/dev/ttyACM1", 115200, 8, 1, 0);
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
