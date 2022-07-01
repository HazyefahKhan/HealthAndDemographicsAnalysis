package health.viewers;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;


import health.computeAnalysis.Model;

/**
 * This class represents a Viewer of type ScatterGraph, which is attached to the Model and creates a Scatter Graph according to the data in the Model.
 * The graph is updated accordingly when changes are made to the model.
 * @author Dheeraj Choppara
 *
 */
public class ScatterGraph extends Viewer implements Observer {
	
	/**
	 * @param Model object is attached to the specific Viewer
	 */
	public ScatterGraph(Model subject) {
		this.setSubject(subject); 
		subject.attach(this);
	}

	/**
	 * Viewer is updated according to the new values in the Model
	 * (via the observer pattern)
	 */
	public void update(Subject changedSubject) {
		if (changedSubject.equals(this.getSubject()))
			updateValues();
	}
	
	/**
	 * Scatter Graph is constructed using the data in the Model
	 * Dynamically creates graph for 1-3 Series data items
	 * ChartPanel object is created with the appropriate data and stored in private attribute JComponent graph;
	 */
	private void updateValues() {
		
		//Retrieve data from latest Model
		int numberOfSeries = this.getSubject().getNumOfSeries();
		double[][] values = this.getSubject().getValues();
		int[] years = this.getSubject().getYears();
		
		XYPlot plot = new XYPlot();
		XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		
		//Create appropriate graph based on the number of series

		//Every graph will have atleast one series
		String seriesTitle1 = this.getSubject().getSeries1(); 
		TimeSeries series1 = new TimeSeries(seriesTitle1);
		
		//Add first series data to graph
		for(int i=0; i<years.length; i++) {
			series1.add(new Year(years[i]),values[0][i]);
		}
		dataset.addSeries(series1);
		
		//Case where there are atleast 2 series of data
		if (numberOfSeries >= 2) {
			XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);
			String seriesTitle2 = this.getSubject().getSeries2(); 
			TimeSeries series2 = new TimeSeries(seriesTitle2);
			
			//Add second series data to graph
			for(int i=0; i<years.length; i++) {
				series2.add(new Year(years[i]),values[1][i]);
			}
			
			//Second series will be displayed respective to its own axis
			TimeSeriesCollection dataset2 = new TimeSeriesCollection();
			dataset2.addSeries(series2);
			plot.setDataset(1, dataset2);
			plot.setRenderer(1, itemrenderer2);
			plot.setRangeAxis(1, new NumberAxis(""));
			plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
		}
		
		//Case where there are 3 series of data
		if (numberOfSeries == 3) {
			String seriesTitle3 = this.getSubject().getSeries3(); 
			TimeSeries series3 = new TimeSeries(seriesTitle3);
			
			//Add third series data to the graph
			for(int i=0; i<years.length; i++) {
				series3.add(new Year(years[i]),values[2][i]);
			}
			//Third series will be displayed respective to the first data set axis
			dataset.addSeries(series3); 
		}
		
		//Continued case where there is atleast one series
		plot.setDataset(0, dataset);
		plot.setRenderer(0, itemrenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));
		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		
		JFreeChart scatterChart = new JFreeChart(this.getSubject().getAnalysisType(),new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(scatterChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		//ChartPanel object stored as an attribute 
		this.setGraph(chartPanel);
		
	}

}
