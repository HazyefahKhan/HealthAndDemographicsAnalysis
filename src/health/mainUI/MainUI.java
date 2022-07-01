package health.mainUI;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.*;

import health.computeAnalysis.Recalculate;
import health.computeAnalysis.UserRequest;
import health.popup.*;
import health.viewers.Viewer;

/**
 * 
 * MainUI is the class that represents the general user interface
 * @author arteen
 *
 */

public class MainUI extends JFrame implements ActionListener {
	
	private int count = 0;
	private JPanel west;
	private JPanel east;
	
	//req will store all the user inputs
	private UserRequest req = new UserRequest();
	
	private static final long serialVersionUID = 1L;
	
	private static MainUI instance;
	
	private File countriesDB = new File("country_list.csv");
	
	/**
	 * Get a single instance of the MainUI (Singleton)
	 * @return single instance of the main UI
	 */
	public static MainUI getInstance() {
		if (instance == null)
			instance = new MainUI();

		return instance;
	}
		
	/**
	 * All the label, button and panel construction of the UI
	 */
	private MainUI() {
		
		super("Health and Demographic Analysis");

		//top panel construction
		
		JLabel countryChoose = new JLabel("Choose a country: ");
		
		Vector<String> countryNames = new Vector<String>();
		
		
		// The valid countries for analysis are listed at the top for ease of access
		ArrayList<String> validCountries = req.getValidCountries();
		for(String country : validCountries) {
			countryNames.add(country);
		}
		
		// the rest of the countries are listed by reading from the Country Database
		try {
			CSVReader DBscanner = new CSVReader(new FileReader(countriesDB));
			DBscanner.readNext();
			String[] countryInfo;
			
			while ((countryInfo = DBscanner.readNext()) != null) {
				// if the countries are not the five already added in countryNames, then they are added to the drop down menu
				if(!validCountries.contains(countryInfo[1])) {
					countryNames.add(countryInfo[1]);
				}
			}
			DBscanner.close();
		// if there is an error reading from the database, a pop-up is displayed
		} catch(FileNotFoundException e) {
			new PopUpCreator().createPopUp("database1");
			e.printStackTrace();
		} catch (Exception e1) { 
			new PopUpCreator().createPopUp("database2");
			e1.printStackTrace();
		}
	
		//drop down for country selection
		JComboBox<String> countriesList = new JComboBox<String>(countryNames);
		countriesList.setSelectedIndex(-1);
		
		// puts the selected country into userReq
		countriesList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				req.setCountry((String) countriesList.getSelectedItem());
			}
		});
		
		
		
		//labels for to and from
		JLabel from = new JLabel("From: ");
		JLabel to = new JLabel("To: ");
		Vector<String> years = new Vector<String>();
				
		for (int i = 2021; i >= 1962; i--) {
			years.add("" + i);
		}
		
		JComboBox<String> fromList = new JComboBox<String>(years);
		
		//initializes the drop down to a blank selection
		fromList.setSelectedIndex(-1);
		
		//puts the start year into userReq
		fromList.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String x = (String) fromList.getSelectedItem();
				
				int tmp = Integer.parseInt(x);
				req.setStartYear(tmp);
			}
		});
		
		
		
		JComboBox<String> toList = new JComboBox<String>(years);
		toList.setSelectedIndex(-1);
		
		//puts the end year into userReq
		toList.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String y = (String) toList.getSelectedItem();
				
				int tmp2 = Integer.parseInt(y);
				req.setEndYear(tmp2);
			}
		});
		
		//making the top panel and adding everything into it
		JPanel top = new JPanel();
		top.add(countryChoose);
		top.add(countriesList);
		top.add(from);
		top.add(fromList);
		top.add(to);
		top.add(toList);
		
		//Bottom
		
		
		JButton recal = new JButton("Recalculate");
		//on clicking recalculate button, sends to actionPerformed
		recal.addActionListener(this);
		
		
		
		//adds the viewers and drop down menu
		JLabel viewers = new JLabel("Available Viewers: ");
		
		Vector<String> viewerNames = new Vector<String>();
		viewerNames.add("Pie Chart");
		viewerNames.add("Bar Chart");
		viewerNames.add("Scatter Plot");
		viewerNames.add("Line Chart");
		viewerNames.add("Report");
		
		JComboBox<String> viewersList = new JComboBox<String>(viewerNames);
		viewersList.setSelectedIndex(-1);
		
		
		JButton add = new JButton("+");
		
		//on + button, will add the viewer to viewer list, if permissible
		add.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				req.addViewer((String) viewersList.getSelectedItem());
			
			}
		});
		

		
		JButton sub = new JButton("-");
		
		//on - button, will remove viewer from viewer list, if permissible
		sub.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				req.removeViewer((String) viewersList.getSelectedItem());
				
			}
		});
		
		JLabel chooseAnalysis = new JLabel("Choose analysis type: ");
		
		Vector<String> analysisTypes = new Vector<String>();
		analysisTypes.add("CO2 emissions vs Energy use vs PM2.5 air pollution");
		analysisTypes.add("PM2.5 air pollution vs Forest area");
		analysisTypes.add("Ratio of CO2 emissions and GDP per capita");
		analysisTypes.add("Average Forest area for selected years");
		analysisTypes.add("Average of Government expenditure on education for selected years");
		analysisTypes.add("Ratio of Hospital beds (per 1,000 people) and Current health expenditure (per 1,000 people)");
		analysisTypes.add("Current health expenditure per capita vs Infant mortality rate (per 1,000 live births)");
		analysisTypes.add("Ratio of Government expenditure on education vs Current health expenditure");
		
		JComboBox<String> analysisList = new JComboBox<String>(analysisTypes);
		analysisList.setSelectedIndex(-1);
		
		//puts the selected analysis into userReq
		analysisList.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				boolean temp = req.setAnalysisType((String) analysisList.getSelectedItem());
				if(!temp) {
					analysisList.setSelectedIndex(-1);
				}
			}
		});
		
		
		//makes bottom panel and adds everything into it
		JPanel bottom = new JPanel();
		
		bottom.add(viewers);
		bottom.add(viewersList);
		bottom.add(add);
		bottom.add(sub);
		bottom.add(chooseAnalysis);
		bottom.add(analysisList);
		bottom.add(recal);
		
		//create west and east panel for charts and report
		
		this.east = new JPanel();
		
		this.west = new JPanel();
		west.setLayout(new GridLayout(2, 3));
		
		
		
		//put panels onto the GUI
		getContentPane().add(top, BorderLayout.NORTH);
		getContentPane().add(bottom, BorderLayout.SOUTH);
		getContentPane().add(west, BorderLayout.WEST);

		
		
		
		
	
		
	}
	
	
	/**
	 * Main to generate and run the UI
	 */
	public static void main(String[] args) {

		JFrame frame = MainUI.getInstance();
		frame.setSize(1250, 650);
		//frame.pack();
		frame.setVisible(true);		
		
				
		
	}

	@Override
	/**
	 * On recalculate click, displays the viewers
	 */
	public void actionPerformed(ActionEvent e) {
		//continue only if user has made all selections
		if(req.check()) {

			//if there are already displayed viewers, remove them
			if(count!= 0) {
				west.removeAll();
			}
			//identify all the viewers in the viewer list and display them
			Recalculate temp = new Recalculate(req);
			Viewer[] graphs = req.getViewers();
			if(temp.dataIsFound()) {
				for (int i=0; i<graphs.length; i++){
					west.add(graphs[i].getGraph());
				}
				JFrame frame = MainUI.getInstance();
				frame.pack();
				count++;
			}
			else {
				west.removeAll();
			}
		}

		// updates MainUI so that the viewers are automatically displayed
		MainUI.getInstance().revalidate();
	}
	
	
	
}


