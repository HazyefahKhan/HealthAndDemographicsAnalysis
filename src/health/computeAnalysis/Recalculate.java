package health.computeAnalysis;

import health.popup.PopUpCreator;

/**
 * Class that initiates the fetching and storage of country data after the recalculate button is pressed
 * 
 * @author Hazyefah Khan
 */
public class Recalculate {

	/**
<<<<<<< HEAD
=======
	 * Factory method used to call strategy.
>>>>>>> branch 'master' of https://repo.csd.uwo.ca/scm/compsci2212_w2021/group26.git
	 * Constructor of the Recalculate class. Resets the Model and executes the appropriate analysis according 
	 * to the user selection
	 * @param request the UserRequest object storing details of the user selection
	 */
	public Recalculate(UserRequest request) {
		//reset previous Model selections
		Model.getInstance().reset();
		//Create appropriate analysis object
		Factory analysisFactory = new Factory();
		Analysis analysis = new Analysis(analysisFactory.createAnalysis(request.getAnalysisType()));
		analysis.executeAnalysis(request);	
		
		//check if model is empty
		//return a error pop-up
		if(Model.getInstance().isEmpty()) {
			new PopUpCreator().createPopUp("years");
		}
	}
	
	/**
	 * dataIsFound checks if there is data in the Model object (in other words, if not all values are set to 0)
	 * @return false if the model object is empty, true if otherwise
	 */
	public Boolean dataIsFound() {
		if(Model.getInstance().isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
}
