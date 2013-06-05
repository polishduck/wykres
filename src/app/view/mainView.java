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
    private static final int MAX_LENGTH = 128;
	public byte[] data = new byte[MAX_LENGTH];
	public ITrace2D trace = new Trace2DLtd(128);
    
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

	public void zapisz(byte[] data) {
		this.data = data;
		for (int i=0; i<128; i++) {
			trace.addPoint(i, this.data[i]);
		}
	}
		

}