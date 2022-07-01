package health.analysisComputations;

import health.computeAnalysis.Data;
import health.computeAnalysis.Model;
import health.computeAnalysis.UserRequest;

/**
 * Strategy to obtain data and model required
 * to render this analysis
 * @author Hazyefah Khan
 */
public class HospitalBedRatio implements ComputeAnalysis{
	/**
	 * Strategy used for specific analysis, designed so it takes into account how many
	 * calls are required to WorldBankAPI and what series graphs each analysis needs
	 * Retrieves all values required for analysis and stores them in model object
	 * Model object is used by viewers to display the analysis in chart style
	 * @param request the UserRequest storing the parameters for data retrieval
	 */
	public void computeAnalysis(UserRequest request) {

		// Calls made to WorldBank API to retrieve all necessary data for this analysis
		Data analysisOne = new FetchData(request, "SH.MED.BEDS.ZS").getData();
		Data analysisTwo = new FetchData(request, "SH.XPD.CHEX.PC.CD").getData();
		
		// Initializing double array to be used to hold values from data
		double[] analysisOneValues;
		double[] analysisTwoValues;
		
		// Data from data object being transferred to local double arrays
		analysisOneValues = analysisOne.getValues();
		analysisTwoValues = analysisTwo.getValues();
		
		// Computation to go from Current health expenditure per capita to
		// Current health expenditure per 1000 people
		for (int i = 0; i < analysisTwoValues.length; i++) {
			analysisTwoValues[i] *= 1000;
		}
		
		
		// 2D Array that hold values arrays from each respective WorldBank API call
		double[][] analysisValues = {analysisOneValues, analysisTwoValues};
		
		
		// Initializing model object to store data obtained so far
		Model model = Model.getInstance();
		
		// Title of analysis type to be displayed on graph
		model.setAnalysisType("Ratio of Hospital Beds and Current Health expenditure");
		// All years data was obtained from (years are consistent for every analysis call
		// Only needs to be obtained from any of the analysis calls
		model.setYears(analysisOne.getYears());
		// Set number of series of specific analysis type
		model.setNumOfSeries(2);
		// Setting specific series names based off analysis type
		model.setSeries1("Hospital beds (per 1,000 people)");
		model.setSeries2("Current health expenditure (per 1,000 people) ");
		// Stores 2D analysisValues array into model
		model.setValues(analysisValues);
		
		
	}
}
