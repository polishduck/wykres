package app.view;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import sun.security.util.Length;


import app.controller.*;
import app.model.*;

public class mainView extends JFrame{
	
	public Timer timer;
    public TimerTask task;
    public int jj=0;
    private static final int MAX_LENGTH = 128;
	public byte[] data = new byte[MAX_LENGTH];
	public ITrace2D trace = new Trace2DLtd(128);
    
	public mainView(){
		setTitle("Wyswietlanie wykresow");
		setBackground(Color.white);
		setSize(400,300);
		final float[] data = new float[128];
	//	for (int k=1; k<128; k++) {
		//	data[k] = 0;				
	//	}
//		System.out.print("wielkosc:" + data.length + "\n");
	//	System.out.println("w my view:" + Arrays.toString(data));
  		Chart2D chart = new Chart2D();
		trace.setColor(Color.BLUE);
		
		getContentPane().add(chart);

		chart.addTrace(trace); 	

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //final 
        //String daneOut = "";

		setVisible(true);
		
		
		int size = data.length;
	  
		timer = new Timer(true);
	    task = new TimerTask() {

	      private double m_y = 0;
	      private long m_starttime = System.currentTimeMillis();
	      /**
	       * @see java.util.TimerTask#run()
	       */

	      @Override
	      public void run() {
	  		int pp = 0;
			
			File file;
			file = new File("file.dat");
			String dane = "";
				
			
		//		System.out.print(data[i]);
			
			
	    	  /*
	          try {
	        	  
	        	  
	        	  
	         	 
	         /*     BufferedReader odczytaj = new BufferedReader(new FileReader(file));
	             // do {         
	              		while ((dane = odczytaj.readLine()) != null) {
	              			data[pp] = Float.parseFloat(dane);
	              			pp++;
	              			trace.addPoint(pp,Float.parseFloat(dane));
	              		}
	              	pp++;
	              	odczytaj = new BufferedReader(new FileReader(file));
	            //  } while (pp < 128);
	             
	          } catch (IOException e) {
	          	System.out.print("dupa");
	          }
	        */
	        
	       }
	    };
		//timer.schedule(task, 1, 2);
	    timer.schedule(task,0,20);
	    
	}

	public void zapisz(byte[] data) {
	//	System.out.print("\n\n\n\ndupa");
		this.data = data;
		
		//this.data[50] = 40;
		
		for (int i=0; i<128; i++) {
			trace.addPoint(i, this.data[i]);
		}
	}
		

}