package health.computeAnalysis;

import health.analysisComputations.ComputeAnalysis;

/**
 * context class containing methods to trigger computeAnalysis Design Pattern
 * @Hazyefah Khan
 */
public class Analysis {
	private ComputeAnalysis strategy;
	
	/**
<<<<<<< HEAD
	 * Constructor for the analysis class. Sets the strategy instance variable
=======
	 *  Analysis constructor class consisting of strategy to be called
>>>>>>> branch 'master' of https://repo.csd.uwo.ca/scm/compsci2212_w2021/group26.git
	 * @param strategy the subclass of ComputeAnalysis to be done
	 */
	public Analysis(ComputeAnalysis strategy) {
		this.strategy = strategy;
	}
	
	/**
<<<<<<< HEAD

	 * 
=======
>>>>>>> branch 'master' of https://repo.csd.uwo.ca/scm/compsci2212_w2021/group26.git
	 * Calls the computeAnalysis method of the subclass stored in strategy, to initiate data retrieval from API
	 * @param request the UserRequest containing parameters for data retrieval
	 */
	public void executeAnalysis(UserRequest request) {
		strategy.computeAnalysis(request);
	}
}
