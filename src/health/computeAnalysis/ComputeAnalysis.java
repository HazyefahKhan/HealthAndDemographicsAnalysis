package health.computeAnalysis;

/**
 * interface for strategy design pattern
 * @author Hazyefah Khan
 */
public interface ComputeAnalysis {
	

	/**
	 * Strategy used for each specifi analysis based off user's request
	 * @param request
	 */
	public void computeAnalysis(UserRequest request);
	
}
