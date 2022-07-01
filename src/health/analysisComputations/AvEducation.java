package health.analysisComputations;

import health.computeAnalysis.Data;
import health.computeAnalysis.Model;
import health.computeAnalysis.UserRequest;

/**
 * Strategy to obtain data and model required
 * to render this analysis
 * @author Hazyefah Khan
 */
public class AvEducation implements ComputeAnalysis{

	/**
	 * Strategy used for specific analysis, designed so it takes into account how many
	 * calls are required to WorldBankAPI and what series graphs each analysis needs
	 * Retrieves all values required for analysis and stores them in model object
	 * Model object is used by viewers to display the analysis in chart style
	 * @param request the UserRequest storing the parameters for data retrieval
	 */
	public void computeAnalysis(UserRequest request) {

		// Calls made to WorldBank API to retrieve all necessary data for this analysis
		Data analysisOne = new FetchData(request, "SE.XPD.TOTL.GD.ZS").getData();
		
		// Initializing double array to be used to hold values from data
		double[] analysisOneValues;
		
		// Data from data object being transferred to local double arrays
		analysisOneValues = analysisOne.getValues();
		
		// 2D Array that hold values arrays from each respective WorldBank API call
		double[][] analysisValues = {analysisOneValues};
		
		
		// Initializing model object to store data obtained so far
		Model model = Model.getInstance();
		
		// Title of analysis type to be displayed on graph
		model.setAnalysisType("Government expenditure on education");
		// All years data was obtained from (years are consistent for every analysis call
		// Only needs to be obtained from any of the analysis calls
		model.setYears(analysisOne.getYears());
		// Set number of series of specific analysis type
		model.setNumOfSeries(1);
		// Setting specific series names based off analysis type
		model.setSeries1("Government expenditure on education, total (% of GDP)");
		// Stores 2D analysisValues array into model
		model.setValues(analysisValues);
		
		
	}
}
