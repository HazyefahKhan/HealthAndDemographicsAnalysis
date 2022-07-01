package health.analysisComputations;

import health.computeAnalysis.UserRequest;

/**
 * interface for strategy design pattern
 * @author Hazyefah Khan
 */
public interface ComputeAnalysis {
	
	/**
	 * computeAnalysis method makes call to fetch data classes and populates Model class with appropriate values
	 * @param request the UserRequest object storing the parameters for data retrieval
	 */
	public void computeAnalysis(UserRequest request);
	
}
