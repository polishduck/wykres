package app.view;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
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

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


import app.controller.*;
import app.model.*;

public class mainView extends JFrame{
	
	public mainView(mainController m){
		setTitle("Wyswietlanie wykresow");
		setBackground(Color.white);
		setSize(400,300);
		final float[] data = new float[10];
		
		Chart2D chart = new Chart2D();
		ITrace2D trace = new Trace2DSimple();
		
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
							  for(int i=0;i>=10;i++){
									//trace.addPoint(i,random.nextDouble()*10.0+i);
								  	data[i] = Float.parseFloat(strLine) ;
							  }

							  System.out.println (strLine);
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
		

		chart.addTrace(trace); 
		
		
		for(int i=10;i>=0;i--){
			trace.addPoint(i,data[i]);
		}

		getContentPane().add(chart);
	}
}