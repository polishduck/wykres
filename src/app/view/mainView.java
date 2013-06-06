package app.view;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

import java.awt.Color;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class mainView extends JFrame{
	
	public Timer timer;
    public TimerTask task;
    public int jj=0;
    private static final int MAX_LENGTH = 256;
	public int[] data = new int[MAX_LENGTH];
	public ITrace2D trace = new Trace2DLtd(256);
    
	public mainView(){
		setTitle("Wyswietlanie wykresow");
		setBackground(Color.white);
		setSize(800,600);
  		Chart2D chart = new Chart2D();
		trace.setColor(Color.BLUE);
		getContentPane().add(chart);

		chart.addTrace(trace); 	

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void zapisz(int[] data2) {
		this.data = data2;
		int[] data1 = new int[256];
		int ii=0;
		for (int i=0; i<256; i++) {
			data1[i] = data[ii]+data[++ii]<<8;
			ii++;
		}
		for (int i=0; i<256; i++) {
			trace.addPoint(i, data1[i]);
		}
		
	}
		

}