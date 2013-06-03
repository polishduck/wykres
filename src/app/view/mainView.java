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
		final float[] data = new float[1000];
		for (int k=1; k<1000; k++) {
			data[k] = 0;				
		}
		

		Chart2D chart = new Chart2D();
		final ITrace2D trace = new Trace2DLtd(200);
		trace.setColor(Color.BLUE);

		chart.addTrace(trace); 
//		

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar=new JMenuBar();
		JMenu menu = new JMenu("Aplikacja");
		menuBar.add(menu);
		
		final JFileChooser fc = new JFileChooser();
		
		JMenuItem menuItem1 = new JMenuItem("Otworz plik danych");
		JMenuItem menuItem2 = new JMenuItem("Zamknij");
		
		menuItem1.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(mainView.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					System.out.print("Opened file " + file.getName() + "\n");
					try {
						  FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
						  // Get the object of DataInputStream
						  DataInputStream in = new DataInputStream(fstream);
						  BufferedReader br = new BufferedReader(new InputStreamReader(in));
						  String strLine;
						  while ((strLine = br.readLine()) != null)   {
							  // Print the content on the console
							  for(int ii=0;ii<1000;ii++){
							//		trace.addPoint(ii,3*10.0+ii);
								  	data[ii] = Float.parseFloat(strLine);
							  }

						  }
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		menuItem2.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItem2.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(menuItem1);
		menu.add(menuItem2);
		setJMenuBar(menuBar);
		
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
	        if (jj == 1000){
	        	System.out.println("wychodzi");
	        	cancel();
	        }
	       }
	    };
		timer.schedule(task, 10, 20);
	}
}