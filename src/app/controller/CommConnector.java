package app.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;


import app.view.mainView;


import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class CommConnector {
	

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
             //   byte[] data = null;
                int[] data = null;
				try {
					System.out.println("czyta");
			//		data = serialPort.readBytes();
					data = serialPort.readIntArray();
				} catch (SerialPortException e) {
        			for(int l=0; l<listeners.size(); l++)
        				listeners.get(l).readingError();
				}
				for(int l=0; l<listeners.size(); l++)
					try {
						listeners.get(l).messageReceived(data);
					} catch ( Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}       
            }
        }
    }
    
    public class SimpleCommListener implements CommListenerInterface {
    	private static final int MAX_LENGTH = 512;
    	private int[] data = new int[MAX_LENGTH];
    	private int counter = 0;
		public mainView myView;
    	
    	int c;
		public void messageReceived(int[] message) {
			System.out.println("package size: " + message.length + "\n");
			System.out.println("full package: " + Arrays.toString(message) + "\n");
			int len = message.length;
			int count_pack = len/128;
			for (int i =0; i<len; i++){
				if (len > 512) {
						break;
				}
				data[c++]=message[i];
			}
				
			if (c == 512){
				wyslij(data);
				c = 0;
			}
		}
		private void wyslij(int[] data) {
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