package health.login;

import java.util.Scanner;
import health.popup.PopUpCreator;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The Proxy class for the login interface. Controls access to the real Login class by first checking if their credentials are valid against the credentials database
 * @author Johann Cardenas
 */
public class LoginProxy implements LoginInterface{

	/**
	 * Stores the inputed username
	 */
	private String username;
	/**
	 * Stores the inputed password
	 */
	private String password;
	/**
	 * Stores a reference to the real login class
	 */
	private LoginInterface realLogin;
	/**
	 * sets the credentials database
	 */
	private File credentialsDB = new File("CredentialsDatabase.txt");		

	
	/**
	 * The constructor for LoginProxy. Sets the values for username and password. Called by LoginUI.
	 * @param username the inputed username
	 * @param password the inputed password
	 */
	public LoginProxy(String username, String password) {
		this.username = username;
		this.password = password;
		this.realLogin = new Login();
	}
	
	/**
	 * The enterSystem() method which enters the system if the credentials are valid, checking against the credentials database
	 */
	public void enterSystem() {
		
		// calls the checkCredentials helper method. If true is returned, the real Login class is accessed
		if(checkCredentials()) {
			realLogin.enterSystem();
		}
		// else an invalid login credentials pop-up is displayed
		else {
			new PopUpCreator().createPopUp("login");
		}
	}
	
	/**
	 * The method responsible for checking if the user credentials are valid
	 * @return true if username and password are stored in the credentials database, false otherwise
	 */
	private boolean checkCredentials() {
		
		// uses a scanner to check if credentials are stored in the credentialsDB
		try {
			Scanner sc1 = new Scanner(credentialsDB);
			
			while (sc1.hasNextLine()) {
				String line = sc1.nextLine();
				String[] testCredentials = line.split(",");
				if(username.equals(testCredentials[0])) {
					if(password.equals(testCredentials[1])) {
						sc1.close();
						return true;
					}
				}
			}
			sc1.close();
		} catch(FileNotFoundException e) {
			new PopUpCreator().createPopUp("database1");
			//PopUpCreator.getInstance().createPopUp("database1");
			e.printStackTrace();
		} catch(Exception e1) {
			new PopUpCreator().createPopUp("database2");
			//PopUpCreator.getInstance().createPopUp("database2");
			e1.printStackTrace();
		}
		
		return false;
		
	}	
}
