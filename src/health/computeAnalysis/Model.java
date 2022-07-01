package health.computeAnalysis;
import health.viewers.Subject;

/**
 * Model stores the fetched and processed data from the analysis classes, for use by the viewers
 * @author Hazyefah Khan
 */
public class Model extends Subject {
	private static Model instance = null;
	private String analysisType;
	private double values [][];
	private int years [];
	private int numSeries;
	private String series1;
	private String series2;
	private String series3;
	
	/**
	 * Constructor to initialize the model
	 */
	private Model() {
		
	}
	
	/**
<<<<<<< HEAD
=======
	 *  Returns instance of model
>>>>>>> branch 'master' of https://repo.csd.uwo.ca/scm/compsci2212_w2021/group26.git
	 * getInstance returns a reference to the one instance of the Model object
	 * @return instance
	 */
	public static Model getInstance() {
		if(instance == null)
			instance = new Model();
		return instance;
		
	}
	
	/**
	 * Accessor method for the analysisType instance variable
	 * @return analysisType the type of analysis completed
	 */
	public String getAnalysisType() {
		return this.analysisType;
	}
	
	/**
	 * Accessor method for the years instance variable
	 * @return years the array storing the years of the data fetched
	 */
	public int[] getYears() {
		return this.years;
	}
	
	/**
	 * Accessor method for the values instance variable
	 * @return values an array of arrays storing the values for each series completed
	 */
	public double[][] getValues() {
		return this.values;
	}
	
	
	/**
	 * Accessor method for the numSeries instance variable
	 * @return numSeries the number of series fetched
	 */
	public int getNumOfSeries() {
		return this.numSeries;
	}
	
	/**
	 * Accessor method for the series1 instance variable
	 * @return series1 the name of the first series of values fetched
	 */
	public String getSeries1() {
		return this.series1;
	}
	
	/**
	 * Accessor method for the series2 instance variable
	 * @return series2 the name of the second series of values fetched
	 */
	public String getSeries2() {
		return this.series2;
	}
	
	/**
	 * Accessor method for the series3 instance variable
	 * @return series3 the name of the third series of values fetched
	 */
	public String getSeries3() {
		return this.series3;
	}
	
	/**
	 * Mutator method for the series1 instance variable
	 * @param label the label for the first series fetched
	 */
	public void setSeries1(String label) {
		this.series1 = label;
	}
	
	/**
	 * Mutator method for the series2 instance variable
	 * @param label the label for the second series fetched
	 */
	public void setSeries2(String label) {
		this.series2 = label;
	}
	
	/**
	 * Mutator method for the series3 instance variable
	 * @param label the label for the third series fetched
	 */
	public void setSeries3(String label) {
		this.series3 = label;
	}
	
	/**
	 * Mutator method for the numSeries instance variable
	 * @param num the number of series of the current analysis
	 */
	public void setNumOfSeries(int num) {
		this.numSeries = num;
	}
	
	/**
	 * Mutator method for the analysisType instance variable
	 * @param analysisType the name of the analysis completed
	 */
	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}
	
	/**
	 * Mutator method for the years instance variable
	 * @param years the years values of the of the data fetched
	 */
	public void setYears(int [] years) {
		this.years = years;
	}
	
	/**
	 * Mutator method for the values instance variable. Also notifies the observers to update the viewers
	 * @param values the array of series data arrays that were fetched.
	 */
	public void setValues(double [][] values) {
		this.values = values;
		notifyObservers();
	}
	
	/**
	 * Resets the instance variables of the model object
	 */
	public void reset() {
		this.analysisType = new String();
		this.values = new double[1][1];
		this.years = new int[1];
		this.numSeries = 0;
		this.series1 = new String();
		this.series2 = new String();
		this.series3 = new String();
	}
	

	/**
	 * isEmpty() checks if the Model object is empty, meaning if all the values in the value
	 * instance variable are equal to 0
	 * @return true if all the series stored in values is equal to zero, false if otherwise
	 */
	public boolean isEmpty() {
		boolean empty = true;
		
		for(double[] series : values) {
			for(double singleValue : series) {
				if(singleValue != 0) {
					empty = false;
				}
			}
		}
		
		return empty;
	}
	
}
