package health.computeAnalysis;


/**
 * Data object used to contain the data obtained from the WorldBank API
 * @author Hazyefah Khan
 */
public class Data {
	private String analysisID;
	private int [] years;
	private double[] values;
	
	
	/**
	 * Constructor for the Data class
	 */
	public Data() {
		
	}
	
	
	
	/**
	 * Getter method to return analysisID
	 * @return analysisId
	 */
	public String getAnalysisID() {
		return this.analysisID;
	}
	
	
	/**
	 * Getter method to return years array
	 * @return years
	 */
	public int[] getYears() {
		return this.years;
	}
	
	
	/**
	 * Getter method to return values array
	 * @return values
	 */
	public double[] getValues() {
		return this.values;
	}
	
	/**
	 * Setter method to set analysis id
	 * @param analysisID
	 */
	public void setAnalysisID(String analysisID) {
		this.analysisID = analysisID;
	}
	
	/**
	 * Setter method to set years array
	 * @param years
	 */
	public void setYears(int [] years) {
		this.years = years;
	}
	
	/**
	 * Setter method to set values array
	 * @param values
	 */
	public void setValues(double [] values) {
		this.values = values;
	}
}