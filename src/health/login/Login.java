package health.login;

import health.mainUI.*;

/**
 * The Login class controls access to the Main UI.
 * @author Johann Cardenas
 */
public class Login implements LoginInterface{

	/**
	 * Controls access to the actual system
	 */
	public void enterSystem() {
				
	 
		// Closes the Login UI, disposing the old window
		LoginUI.getInstance().setVisible(false);
		LoginUI.getInstance().dispose();
		
		
		// Displaying the system UI
		MainUI.main(null);
	}
	
	
}
