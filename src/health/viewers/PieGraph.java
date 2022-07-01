package health.viewers;

import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.BorderFactory;
import java.awt.Color;

import health.computeAnalysis.Model;

/**
 * This class represents a Viewer of type PieGraph, which is attached to the Model and creates a Pie Graph according to the data in the Model.
 * The graph is updated accordingly when changes are made to the model.
 * Pie Graph is only equipped to handle averages in a one-series Model
 * @author Dheeraj Choppara
 *
 */
public class PieGraph extends Viewer implements Observer {
	
	/**
	 * @param Model object is attached to the specific Viewer
	 */
	public PieGraph(Model subject) {
		this.setSubject(subject); 
		subject.attach(this);
	}
	
	
	/* 
	 * Viewer is updated according to the new values in the Model
	 * (via the observer pattern)
	 */
	public void update(Subject changedSubject) {
		if (changedSubject.equals(this.getSubject()))
			updateValues();
	}
	
	/**
	 * Pie Graph is constructed using the One-Series data in the Model
	 * Calculates an average from the values in the Model and displays the average and 100-average on the Pie Graph
	 * ChartPanel object is created with the appropriate data and stored in private attribute JComponent graph;
	 */
	private void updateValues() {
		
		//Retrieve data from latest Model
		int[] years = this.getSubject().getYears();
		double[][] values = this.getSubject().getValues();
		double sum = 0;
		int numOfValidYears=0;
		
		for (int i=0; i<years.length;i++) {
			//Ignore years where there is no data retrieved
			if(values[0][i] !=0) {
				sum = sum + values[0][i];
				numOfValidYears++;
			}
		}
		
		//Calculate average 
		double avg = sum/numOfValidYears;
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		//Set Pie chart to show 1. The average and 2. The remaining portion (100-average)
		dataset.setValue(this.getSubject().getSeries1(),avg);
		String oppositeSeries = "Non-" + this.getSubject().getSeries1();
		dataset.setValue(oppositeSeries,100-values[0][0]);
		
		JFreeChart pieChart = ChartFactory.createPieChart(this.getSubject().getAnalysisType(), dataset, true, true, false);
		ChartPanel chartPanel = new ChartPanel(pieChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		//ChartPanel object stored as an attribute
		this.setGraph(chartPanel);
	}

}
