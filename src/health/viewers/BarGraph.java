package health.viewers;

import health.computeAnalysis.Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * This class represents a Viewer of type BarGraph, which is attached to the Model and creates a Bar Graph according to the data in the Model.
 * The graph is updated accordingly when changes are made to the model.
 * @author Dheeraj Choppara
 *
 */
public class BarGraph extends Viewer implements Observer {
	
	/**
	 * @param Model object is attached to the specific Viewer
	 */
	public BarGraph(Model subject) {
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
	 * Bar Graph is constructed using the data in the Model
	 * Dynamically creates graph for 1-3 Series data items
	 * ChartPanel object is created with the appropriate data and stored in private attribute JComponent graph;
	 */
	private void updateValues() {
		
		//Retrieve data from latest Model
		int numberOfSeries = this.getSubject().getNumOfSeries();
		double[][] values = this.getSubject().getValues();
		int[] years = this.getSubject().getYears();
		
		//Create appropriate graph based on the number of series
		CategoryPlot plot = new CategoryPlot();
		
		//Every graph will have atleast one series
		String seriesTitle1 = this.getSubject().getSeries1(); 
		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		
		//Add first series data to graph
		for(int i=0; i<years.length; i++) {
			dataset1.setValue(values[0][i],seriesTitle1,Integer.toString(years[i]));
		}
		BarRenderer barrenderer1 = new BarRenderer();
		
		//Case where there are atleast 2 series of data
		if (numberOfSeries >= 2) {
			String seriesTitle2 = this.getSubject().getSeries2(); 
			DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
			
			//Add second series data to graph
			for(int i=0; i<years.length; i++) {
				dataset2.setValue(values[1][i],seriesTitle2,Integer.toString(years[i]));
			}
			//Second series will be displayed respective to its own axis
			BarRenderer barrenderer2 = new BarRenderer();
			plot.setDataset(1, dataset2);
			plot.setRenderer(1, barrenderer2);
			plot.setRangeAxis(1, new NumberAxis(""));
			plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
		}
		//Case where there are 3 series of data
		if (numberOfSeries == 3) {
			String seriesTitle3 = this.getSubject().getSeries3(); 
			
			//Add third series data to the graph
			for(int i=0; i<years.length; i++) {
				dataset1.setValue(values[2][i],seriesTitle3,Integer.toString(years[i]));
			}
		}
		
		//Continued case where there is atleast one series
		plot.setDataset(0, dataset1);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));
		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		
		JFreeChart barChart = new JFreeChart(this.getSubject().getAnalysisType(), new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
		
		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
      	//ChartPanel object stored as an attribute 
		this.setGraph(chartPanel);
	}
}
