package health.login;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * This class is responsible for creating the Login UI, and saving the user inputted credentials
 * @author Johann Cardenas
 */
public class LoginUI extends JFrame implements ActionListener{
	
	
	/**
	 * Defining the Version Number
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Defines the login window
	 */
	private Container container = getContentPane();
	/**
	 *  Defines the username label
	 */
	private JLabel userLabel = new JLabel("Username:");
	/**
	 *  Defines the password label
	 */
	private JLabel passwordLabel = new JLabel("Password:");
	/**
	 *  Defines the text field used to input the username
	 */
	private JTextField userField = new JTextField();
	/**
	 *  Defines the text field used to input the password
	 */
	private JPasswordField passwordField = new JPasswordField();	
	/**
	 *  Defines the button to submit credentials
	 */
	private JButton loginButton = new JButton("Submit!");
	
	/**
	 * static instance variable is defined to comply with Singleton design pattern
	 */
	private static LoginUI instance = null;

	/**
	 * getInstance() ensures that only one LoginUI will exist at any given time
	 * @return a reference to the one LoginUI object
	 */
	public static LoginUI getInstance() {
		if (instance == null)
			instance = new LoginUI();

		return instance;
	}
	
	/**
	 * The constructor, which adds the buttons and sets the dimensions of the system.
	 */
	private LoginUI() {
		setLayoutManager();
		setComponents();
		addComponents();
		addActionEvent();
		
		this.setBounds(10,10,330,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getRootPane().setDefaultButton(loginButton);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * setLayoutManager() is responsible for setting the layout of the UI
	 */
	public void setLayoutManager() {
		container.setLayout(null);
	}
	
	/**
	 *  setComponents(), which is responsible for setting the dimensions of the components
	 */
	public void setComponents() {
		userLabel.setBounds(10,10,100,30);
		userField.setBounds(80,16,230,20);
		
		passwordLabel.setBounds(10,40,100,30);
		passwordField.setBounds(80,46,230,20);
		loginButton.setBounds(120,70,100,30);
	}
	
	/**
	 * addComponents(), responsible for adding all components to the Login Window
	 */
	public void addComponents() {
		container.add(userLabel);
		container.add(passwordLabel);
		container.add(userField);
		container.add(passwordField);
		container.add(loginButton);
		
	}
	
	/**
	 *  Responsible for adding action events, such as what happens when the login button is pressed
	 */
	public void addActionEvent() {
		loginButton.addActionListener(this);
	}
	
	/**
	 * Displays the login UI when the LoginUI class is ran.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frame = LoginUI.getInstance();
		frame.setVisible(true);

	}
	
	/**
	 * Invoked when an action occurs in the Login UI
	 * @param e the action that occurs
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==loginButton) {
			String userInput;
			String passwordInput;
			
			// the username and password are stored as strings, and then a login attempt
			// is made by creating a LoginProxy object and calling enterSystem()
			userInput = userField.getText();
			passwordInput = String.valueOf(passwordField.getPassword());
			LoginInterface LoginAttempt = new LoginProxy(userInput,passwordInput);
			LoginAttempt.enterSystem();
		}
	}

}
