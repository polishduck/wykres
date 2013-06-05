package app.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import app.view.mainView;


import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class CommConnector {
	
//	public mainView myView;
	
	private ArrayList<CommListenerInterface> listeners;
    private SerialPort serialPort;
    private boolean listening;
    
    public CommConnector() {      
        listeners = new ArrayList<CommListenerInterface>();
    }
    
    public void connect(String portName, int baud, int dataBits, int stopBits, int parity) throws SerialPortException {
    	serialPort = new SerialPort(portName);
        serialPort.openPort();
        serialPort.setParams(baud, dataBits, stopBits, parity);
        int mask = SerialPort.MASK_RXCHAR;
        serialPort.setEventsMask(mask);      
    }
    
    public ArrayList<String> getPortsNames() {
        ArrayList<String> ports = new ArrayList<String>();
        
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
        	ports.add(portNames[i]);
        }
        return ports;
    }
    
    public void addListener(CommListenerInterface listener) {
        this.listeners.add(listener);
    }
    
    public void removeListener(CommListenerInterface listener) {
        this.listeners.remove(listener);
    }
    
    public void startPortListening() throws SerialPortException{
    	if(!this.listening) {
    		serialPort.addEventListener(new SerialPortReader());
    		this.listening = true;
    	}
    }
    
    public void stopPortListening() throws SerialPortException {
    	if(this.listening) {
    		serialPort.removeEventListener();
    		this.listening = false;
    	}
    }
    
    public void writeToPort(String s) throws SerialPortException {
    	this.serialPort.writeString(s);
    }

    private class SerialPortReader implements SerialPortEventListener {
    	
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR()){
                byte[] data = null;
				try {
					data = serialPort.readBytes();
				} catch (SerialPortException e) {
        			for(int l=0; l<listeners.size(); l++)
        				listeners.get(l).readingError();
				}
				for(int l=0; l<listeners.size(); l++)
					try {
						listeners.get(l).messageReceived(data);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}       
            }
        }
    }
    
    public class SimpleCommListener implements CommListenerInterface {
    	private byte[] data = new byte[MAX_LENGTH];
    	private int counter = 0;
		public mainView myView;
    	private static final int MAX_LENGTH = 128;
		@Override
		public void messageReceived(byte[] message) throws FileNotFoundException {
			int len = message.length;
//			System.out.println("wielkosc paczki:" +  message.length);
//			System.out.println("cala paczka" + Arrays.toString(message) + "\n");
			int count_pack = len/128;
			
//			File file;
//			file = new File("aa.dat");
			PrintWriter zapis = new PrintWriter("file.dat");
			
			for (int jj=0; jj<count_pack; jj++) {
				for(int i=(0+jj*128); i<(127+jj*128); i++){
					data[counter++]=message[i];
					zapis.println(message[i]);
				}
				wyslij(data);
				
		//		System.out.println((jj+1) + ". paczka" + Arrays.toString(data) + "\n");
				counter=0;
				zapis.close();
				break;
			}	
	//		System.out.println(Arrays.toString(message));
			//deklaracja tablicy o wielkosci 100
			//dochodzi do konca tablicy i konczymy i nastepna zapelniamy
			//dmesg
	/*		int len = message.length;
			if(len+counter>MAX_LENGTH){
				int difference = len+counter-MAX_LENGTH;
				for(int i=0; i<difference;i++)
					data[counter++]=message[i];
				/*
				 * place to send data to network
				 */
	/*			counter=0;
				byte[] remainMessage = Arrays.copyOfRange(message, difference, len);
				messageReceived(remainMessage);
			}
			else{
				for(int i=0; i<len; i++)
					data[counter++]=message[i];
			}
			if(counter==128){
				
			}
	*/	}
		private void wyslij(byte[] data) {
			System.out.println("cala paczka w wyslij" + Arrays.toString(data) + "\n");
			myView.zapisz(data);
			
		}
		@Override
		public void portAlreadyInUse() {}
		@Override
		public void writingError() {}
		@Override
		public void readingError() {}
		
    }
}