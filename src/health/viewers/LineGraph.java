package health.viewers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.BasicStroke;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import health.computeAnalysis.Model;

/**
 * This class represents a Viewer of type LineGraph, which is attached to the Model and creates a Line Graph according to the data in the Model.
 * The graph is updated accordingly when changes are made to the model.
 * @author Dheeraj Choppara
 *
 */
public class LineGraph extends Viewer implements Observer {

	/**
	 * @param Model object is attached to the specific Viewer
	 */
	public LineGraph(Model subject) {
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
	 * Line Graph is constructed using the data in the Model
	 * Dynamically creates graph for 1-3 Series data items
	 * ChartPanel object is created with the appropriate data and stored in private attribute JComponent graph;
	 */
	private void updateValues() {
		
		//Retrieve data from latest Model
		int numberOfSeries = this.getSubject().getNumOfSeries();
		double[][] values = this.getSubject().getValues();
		int[] years = this.getSubject().getYears();
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		//Create appropriate graph based on the number of series
		
		//Every graph will have atleast one series
		String seriesTitle1 = this.getSubject().getSeries1(); 
		XYSeries series1 = new XYSeries(seriesTitle1);
		
		//Add first series data to graph
		for(int i=0; i<years.length; i++) {
			series1.add(years[i],values[0][i]);
		}
		dataset.addSeries(series1);
		
		//Case where there are atleast 2 series of data
		if (numberOfSeries >= 2) {
			String seriesTitle2 = this.getSubject().getSeries2(); 
			XYSeries series2 = new XYSeries(seriesTitle2);
			
			//Add second series data to graph
			for(int i=0; i<years.length; i++) {
				series2.add(years[i],values[1][i]);
			}
			dataset.addSeries(series2);
		}
		
		//Case where there are 3 series of data
		if (numberOfSeries == 3) {
			String seriesTitle3 = this.getSubject().getSeries3(); 
			XYSeries series3 = new XYSeries(seriesTitle3);
			
			for(int i=0; i<years.length; i++) {
				series3.add(years[i],values[2][i]);
			}
			dataset.addSeries(series3);
		}
		
		//Continued case where there is atleast one series
		JFreeChart chart = ChartFactory.createXYLineChart(this.getSubject().getAnalysisType(), "Year", "", dataset,PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(new TextTitle(this.getSubject().getAnalysisType(), new Font("Serif", java.awt.Font.BOLD, 18)));

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		//ChartPanel object stored as an attribute
		this.setGraph(chartPanel);
		
	}
}
