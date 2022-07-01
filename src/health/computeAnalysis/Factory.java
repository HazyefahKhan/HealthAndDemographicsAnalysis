package health.computeAnalysis;

import health.analysisComputations.AirPolAndExposure;
import health.analysisComputations.AvEducation;
import health.analysisComputations.AvForestArea;
import health.analysisComputations.CO2Emissions;
import health.analysisComputations.CO2Ratio;
import health.analysisComputations.ComputeAnalysis;
import health.analysisComputations.GovExVsHealthEx;
import health.analysisComputations.HealthExpenditurePerCapita;
import health.analysisComputations.HospitalBedRatio;
import health.viewers.BarGraph;
import health.viewers.LineGraph;
import health.viewers.PieGraph;
import health.viewers.Report;
import health.viewers.ScatterGraph;
import health.viewers.Viewer;

/**
 * This class returns the appropriate type of object for the Parent Classes of Viewer and ComputeAnalysis
 * by implementing the Factory Class Design Pattern
 *@author Dheeraj Choppara
 *
 */
public class Factory {
	
	/**
     * Creates the appropriate subclass object for Viewers
	 * @param graph
	 * @return Viewer object of the approprite graph subtype 
	 */
	public Viewer createViewer(String graph) {
		
		if(graph.equals("Pie Chart"))
			return new PieGraph(Model.getInstance());
		else if(graph.equals("Bar Chart"))
			return new BarGraph(Model.getInstance());
		else if(graph.equals("Scatter Plot"))
			return new ScatterGraph(Model.getInstance());
		else if(graph.equals("Line Chart"))
			return new LineGraph(Model.getInstance());
		else if(graph.equals("Report"))
			return new Report(Model.getInstance());
		else return null;
	}
		
	/**
     * Creates the appropriate subclass object for ComputeAnalysis
	 * @param analysisType
	 * @return return strategy depending on type of analysis called by user
	 */
	public ComputeAnalysis createAnalysis(String analysisType) {
		if(analysisType.equals("CO2 emissions vs Energy use vs PM2.5 air pollution"))
			return new CO2Emissions();
		else if(analysisType.equals("PM2.5 air pollution vs Forest area"))
			return new AirPolAndExposure();
		else if(analysisType.equals("Ratio of CO2 emissions and GDP per capita"))
			return new CO2Ratio();
		else if(analysisType.equals("Average Forest area for selected years"))
			return new AvForestArea();
		else if(analysisType.equals("Average of Government expenditure on education for selected years"))
			return new AvEducation();
		else if(analysisType.equals("Ratio of Hospital beds (per 1,000 people) and Current health expenditure (per 1,000 people)"))
			return new HospitalBedRatio();
		else if(analysisType.equals("Current health expenditure per capita vs Infant mortality rate (per 1,000 live births)"))
			return new HealthExpenditurePerCapita();
		else if(analysisType.equals("Ratio of Government expenditure on education vs Current health expenditure"))
			return new GovExVsHealthEx();
		else return null;
	}
		
}
