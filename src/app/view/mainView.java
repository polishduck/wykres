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
    
	public mainView(mainController m){
		setTitle("Wyswietlanie wykresow");
		setBackground(Color.white);
		setSize(400,300);
		final float[] data = new float[128];
	//	for (int k=1; k<128; k++) {
		//	data[k] = 0;				
	//	}
		System.out.print("wielkosc:" + data.length + "\n");
		

		Chart2D chart = new Chart2D();
		final ITrace2D trace = new Trace2DLtd(200);
		trace.setColor(Color.BLUE);

		chart.addTrace(trace); 	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int pp = 0;
		
		File file;
		file = new File("aa.dat");
        String dane = "";
        String daneOut = "";
        
        try {
        	 
            BufferedReader odczytaj = new BufferedReader(new FileReader(file));
            
       //     for (int ii = 0;ii<128;ii++) {
            do {
            	
            
            	while ((dane = odczytaj.readLine()) != null) {
                // tutaj dane które odczytasz zapisujesz gdzie chcesz
            		data[pp] = Float.parseFloat(dane);
            		pp++;
            		System.out.print(pp + ")" + dane + "\n");
            		
        //    	}
            	
            //	System.out.print(data[ii] + "\n");
//            	trace.addPoint(pp,Float.parseFloat(dane));
	//			pp++;

            	}
            	pp++;
            	odczytaj = new BufferedReader(new FileReader(file));
            } while (pp < 128);
            
        } catch (IOException e) {
        	System.out.print("dupa");
        }
    

		setVisible(true);
		
		getContentPane().add(chart);
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
	        double rand = Math.random();
	        boolean add = (rand >= 0.5) ? true : false;
	        
	        this.m_y = (add) ? this.m_y + Math.random() : this.m_y - Math.random();
	        trace.addPoint(jj, data[jj]);
	        jj=jj+1;
	        if (jj == 127){
	        	System.out.println("wychodzi");
	        	//cancel();
	        	
	        }
	       }
	    };
		timer.schedule(task, 10, 20);
	}
}