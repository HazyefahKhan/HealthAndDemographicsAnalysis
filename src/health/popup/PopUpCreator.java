package health.popup;

import javax.swing.*;

/**
 * This class is the PopUpCreator, responsible for creating all Pop Ups.
 * @author Johann Cardenas
 */
public class PopUpCreator {
		
	/**
	 * Displays a pop-up depending on the situation, signified by the type String
	 * @param type the type of pop-up to be displays
	 */
	public void createPopUp(String type) {
				
		// Creates a JOptionPane object when called according to the situation.
		switch(type) {
			case "login":
				JOptionPane.showMessageDialog(null, "Invalid Login Credentials!", "Login Error",JOptionPane.WARNING_MESSAGE);
			break;
			case "years":
				JOptionPane.showMessageDialog(null, "Invalid Years Selection! Please choose new start and end years.", "Selection Error",JOptionPane.WARNING_MESSAGE);
			break;
			case "countries":
				JOptionPane.showMessageDialog(null, "Invalid Country Selection! Your country has not been set.", "Selection Error",JOptionPane.WARNING_MESSAGE);	
			break;
			case "database1":
				JOptionPane.showMessageDialog(null, "No Database Found! Your parameters have not been set.", "Database Error",JOptionPane.WARNING_MESSAGE);	
			break;
			case "database2":
				JOptionPane.showMessageDialog(null, "There was an error accessing one of our databases", "Database Error",JOptionPane.WARNING_MESSAGE);	
			break;
			case "viewers1":
				JOptionPane.showMessageDialog(null, "Invalid Viewer Selection for selected analysis! Please add a different type of viewer.", "Selection Error",JOptionPane.WARNING_MESSAGE);	
			break; 
			case "viewers2":
				JOptionPane.showMessageDialog(null, "Viewer already in Viewer List! Please add a different type of viewer.", "Selection Error",JOptionPane.WARNING_MESSAGE);	
			break; 
			case "viewers3":
				JOptionPane.showMessageDialog(null, "Removed viewer not in Viewer List! Please remove a different type of viewer, or add one if list is empty", "Selection Error",JOptionPane.WARNING_MESSAGE);	
			break; 
			case "viewers4":
				JOptionPane.showMessageDialog(null, "Viewer List is empty! Please add a viewer before recalculating.", "Selection Error",JOptionPane.WARNING_MESSAGE);	
			break; 
			case "viewers5":
				JOptionPane.showMessageDialog(null, "Invalid Viewer Selection for selected analysis! Invalid viewers have been removed.", "Selection Error",JOptionPane.WARNING_MESSAGE);	
			break; 
			case "analysis":
				JOptionPane.showMessageDialog(null, "Please select an analysis type!", "Selection Error",JOptionPane.WARNING_MESSAGE);	
			break; 
			case "analysis2":
				JOptionPane.showMessageDialog(null, "Invalid analysis selection for selected country! Analysis selection has been cleared. Please select a different analysis or country.", "Selection Error",JOptionPane.WARNING_MESSAGE);	
			break; 
			case "complete selection":
				JOptionPane.showMessageDialog(null, "Make sure the Start and End Years + At least One Viewer + Analysis Type is Selected","Selection Error",JOptionPane.WARNING_MESSAGE);	
			break;
		}
	}
}
