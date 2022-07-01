package health.computeAnalysis;

import java.util.*;

// CSVReader for easily reading through our databases
import com.opencsv.CSVReader;
import health.popup.PopUpCreator;
import health.viewers.Viewer;
import java.io.*;

/**
 * UserRequest is the class storing all the user input.
 * @author Johann Cardenas
 *
 */
public class UserRequest {

	/**
	 * The first year in the range to fetch data for
	 */
	private int startYear;
	/**
	 * The last year in the range to fetch data for
	 */
	private int endYear;
	/**
	 * The country code according to the country the user has selected 
	 */
	private String country;
	/**
	 * The full name of the user selected country
	 */
	private String countryFull;
	/**
	 * The analysis type the user has selected
	 */
	private String analysis;
	/**
	 * An ArrayList storing Viewer objects, representing the selected viewers 
	 */
	private ArrayList<Viewer> viewersList;
	/**
	 * An ArrayList storing strings of viewer objects, representing the selected viewers 
	 */
	private ArrayList<String> viewersNames;
	/**
	 * The countries database used to validate the country selection
	 */
	private File countriesDB = new File("country_list.csv");
	/**
	 * The database used to validate the viewer selection for the analysis
	 */
	private File analysisDB = new File("AnalysisDatabase.csv");
	/**
	 * Boolean variable holding whether analysis has been set
	 */
	private boolean analysisSet = false;
	/**
	 * ArrayList storing the list of valid countries to choose an analysis from
	 */
	private ArrayList<String> validCountriesList;
	private PopUpCreator popUpCreator;
	
	public UserRequest() {
		this.viewersList = new ArrayList<Viewer>();
		this.viewersNames = new ArrayList<String>();
		this.countriesDB = new File("country_list.csv");
		this.analysisDB = new File("AnalysisDatabase.csv");
		this.analysisSet = false;
		this.validCountriesList = new ArrayList<String>();
		this.popUpCreator = new PopUpCreator();
		buildValidCountriesList();
	}
	
	/**
	 * Mutator method for the startYear instance variable. If an invalid start year is chosen, a pop-up is displayed
	 * @param startYear the user selected start year.
	 */
	public void setStartYear(int startYear) {
		
		//the yearsInvalid and countryFound boolean variables keep track of whether
		// the years are found to be invalid, and if the country has been found in the database
		boolean yearsInvalid = false;
		boolean countryFound = false;
		
		// first check if start year is in valid range according to country database. Only done
		// if country is already selected
		if(null != this.country && !yearsInvalid) {
			try {
				CSVReader DBscanner = new CSVReader(new FileReader(countriesDB));
				String[] countryInfo;
				
				//skips the first line
				DBscanner.readNext();
				while ((countryInfo = DBscanner.readNext()) != null && !countryFound) {
					
					// only checks for selected country so entire database is not read
					if(this.country.equals(countryInfo[5])) {
						if(startYear < (Integer.parseInt(countryInfo[6]))) {
							popUpCreator.createPopUp("years");
							yearsInvalid = true;
						}
						countryFound = true;
					}
				}
				DBscanner.close();
			// if an exception occurs when accessing our database, a pop-up is displayed
			} catch(FileNotFoundException e) {
				popUpCreator.createPopUp("database1");
			} catch (Exception e1) {
				popUpCreator.createPopUp("database2");
				e1.printStackTrace();
			}
		}
		
		// then checks if end year is less than the selected start year 
		if(this.endYear != 0) {
			if(this.endYear < startYear) {
				popUpCreator.createPopUp("years");
				yearsInvalid = true;
			}
		}
		
		// if year selection is proven not to be invalid, the start year selection is stored
		if(yearsInvalid==false) {
			this.startYear = startYear;
		}
		
	}
	
	/**
	 * Mutator method for the endYear instance variable. If an invalid end year is chosen, a pop-up is displayed
	 * @param endYear the user selected end year.
	 */
	public void setEndYear(int endYear) {
		
		//the yearsInvalid and countryFound boolean variables keep track of whether
		// the years are found to be invalid, and if the country has been found in the database
		boolean yearsInvalid = false;
		boolean countryFound = false;
		
		// first check if start year is in valid range according to country database. Only done
		// if country is already selected
		if(null != this.country && !yearsInvalid) {
			try {
				CSVReader DBscanner = new CSVReader(new FileReader(countriesDB));
				
				//skips the first line
				DBscanner.readNext();
				String[] countryInfo;
				
				while ((countryInfo = DBscanner.readNext()) != null && !countryFound) {
					
					// only checks for selected country so entire database is not read
					if(this.country.equals(countryInfo[5])) { 
						
						// if the end year in the database is listed as Now, it is treated as the current year
						int validEndYear;
						if(countryInfo[7].equals("Now")) {
							validEndYear = 2021;
						} else {
							validEndYear = Integer.parseInt(countryInfo[7]);
						}
						
						// if the selected end year is greater than the last valid end year, a pop-up is displayed
						if(endYear > validEndYear) {
							popUpCreator.createPopUp("years");
							yearsInvalid = true;
						}
						countryFound = true;
					}
				}
				DBscanner.close();
			// if an exception occurs when accessing our database, a pop-up is displayed
			} catch(FileNotFoundException e) {
				popUpCreator.createPopUp("database1");
			} catch (Exception e1) {
				popUpCreator.createPopUp("database2");
				e1.printStackTrace();
			}
		}
		// checks if start year is greater than the selected end year. If so, then a pop-up is displayed
		if(this.startYear != 0) {
			if(this.startYear > endYear) {
				popUpCreator.createPopUp("years");
				yearsInvalid = true;
			}
		}
		
		// if year selection is proven not to be invalid, the end year selection is stored
		if(yearsInvalid==false) {
			this.endYear = endYear;
		}
		
	}
	
	/**
	 * The mutator method for the analysis instance variable. Also clears the viewer list if the analysis type has changed
	 * @param analysis the user selected analysis type
	 */
	public boolean setAnalysisType(String analysis) {
		boolean viewersInvalid = false;
		
		// first the analysis is checked to be set. In this case, the list is cleared
		if(analysisSet) {
			
			// checks if analysis selection is invalid for previous country selection. If so, a pop-up is returned.
			// the full name is sent, as full country names are stored in the analysis database
			if(null != this.countryFull) {
				if (checkCountryInvalid(this.countryFull, analysis)) {
					popUpCreator.createPopUp("analysis2");
					return false;
				}	
			}
			
			// also validate if the analysis is different, clearing the viewers
			if(this.analysis != analysis && viewersNames != null) {
				viewersNames.clear();
				viewersList.clear();
			}
			this.analysis = analysis;
			return true;
		}
		
		// if the analysis has not been set, then we want to keep the viewers list
		// the user has selected. In this case, the viewersList is not cleared,
		// but validated against the database to ensure that the viewers are valid for
		// the analysis type
		else {
			
			// checks if analysis selection is invalid for previous country selection. If so, a pop-up is returned.
			// the full name is sent, as full country names are stored in the analysis database
			if(null != this.countryFull) {
				if (checkCountryInvalid(this.countryFull, analysis)) {
					popUpCreator.createPopUp("analysis2");
					return false;
				}	
			}
			
			this.analysis = analysis;
			this.analysisSet = true;
			// viewers are copied to a separate list to prevent errors
			ArrayList<String> oldViewersNames = new ArrayList<String>();
			
			// checks if Viewers have been selected. If they have not, then there
			// is no need to validate them for the selected analysis, and the function is exited
			if (this.viewersNames == null) {
				return true;
			}

			
			for(String viewer: this.viewersNames) {
				oldViewersNames.add(viewer);
			}
			
			// each viewer in the viewerList is checked against the database to see if they're valid or not
			for(String viewer : oldViewersNames) {
				if(checkViewerInvalid(viewer)) {
					viewersInvalid = true;
					// if the viewer is invalid, it is removed from the list
					removeViewer(viewer);
				}
			}

			if(viewersInvalid) {
				popUpCreator.createPopUp("viewers5");
			}
			return true;
		}
	}
	
	/**
	 * Mutator method for the country instance variable
	 * @param country the user selected country
	 */
	public void setCountry(String country) {
		//checks if country is in our valid country list, and also converts country name into country code
		// for use by API
		
		if(validCountriesList.contains(country)) {
			try {
				// CSVReader object is used to read the database
				CSVReader DBscanner = new CSVReader(new FileReader(countriesDB));
				
				String[] countryInfo;
				while ((countryInfo = DBscanner.readNext()) != null) {
					// if the selected country name equals the full country name in the database,
					// the country instance variable is set as the user selected country
					if(country.equals(countryInfo[1])) {
						
						// checks if the country is in the list of invalidCountries for the current analysis selection
						if(analysisSet && checkCountryInvalid(country, this.analysis)) {
								popUpCreator.createPopUp("countries");
						} else {
							this.country = countryInfo[5];
							this.countryFull = country;
						}
					}
				}
				DBscanner.close();
			// if an error occurs while accessing the database, a pop-up is displayed
			} catch(FileNotFoundException e) {
				popUpCreator.createPopUp("database1");
			} catch (Exception e1) {
				popUpCreator.createPopUp("database2");
				e1.printStackTrace();
			}
			
			// check against analysis database country is allowed for analysis type
			
			
		} else {
			// if the selected country is not in the valid list of countries to fetch data from,
			// a pop-up is displayed
			popUpCreator.createPopUp("countries");
		}
	}
	
	/**
	 * A method to add a new viewer to the ViewersList and ViewersNames instance variables
	 * @param newViewer the viewer to be added
	 */
	public void addViewer(String newViewer) {
		
		// checks if the selected viewer has been found to be invalid
		boolean viewerInvalid = false;
		
		// checks if viewers lists are empty. If so, they are initialized
		if(viewersNames == null) {
			viewersNames = new ArrayList<String>();
			viewersList = new ArrayList<Viewer>();
		}
		
		// if the viewersNames instance variable does not contain the selected viewer, a validation is performed
		if(!viewersNames.contains(newViewer)) {
			
			// if the selected viewer is equal to "Viewer", then that is an invalid selection
			if(newViewer.equals("Viewer")) {
				viewerInvalid = true;
			} else {
				// validation to check if viewer is applicable for analysis type. First it is ensured the analysis
				// type has been selected
				if(null != this.analysis) {	
					// calls the validateViewer helper class to ensure that the viewer is valid				
					viewerInvalid = checkViewerInvalid(newViewer);
				}
			}
			
			//if the viewer is valid then it is added to the viewersNames and viewersList instance variables.
			if(!viewerInvalid) {
		        viewersNames.add(newViewer);
		        Factory viewerFactory = new Factory();
		        viewersList.add(viewerFactory.createViewer(newViewer));
			} else {
				// if not a pop-up is displayed
				popUpCreator.createPopUp("viewers1");
			}
			
		}
		// if the selected viewer is already in the list, a pop-up is displayed
		else {
			//error pop-up
			popUpCreator.createPopUp("viewers2");
		}
	}
		
	/**
	 * A method to remove a viewer to the ViewersList and ViewersNames instance variables
	 * @param oldViewer the viewer to be remove
	 */
	public void removeViewer(String oldViewer) {
		
		// if the viewersNames ArrayList is null, then no viewers have yet been added,
		// and a pop-up is displayed prompting the user to first add a viewer
		if(viewersNames == null) {
			//error popup
			popUpCreator.createPopUp("viewers3");
		// if the viewersNames ArrayList contains the old viewer, the it is removed.
		// else a pop-up is displayed, as the viewer requested to be removed is not
		// yet in the viewer list.
		} else {	
			if(viewersNames.contains(oldViewer)) {
		        int position = viewersNames.indexOf(oldViewer);
		        Model.getInstance().detach(viewersList.get(position));
		        viewersList.remove(position);
		        viewersNames.remove(position);
			} else {
				//error popup
				popUpCreator.createPopUp("viewers3");
			}
		}
	}
	
	/**
	 * Accessor method for the start year instance variable
	 * @return the user selected start year
	 */
	public int getStartYear() {
		return startYear;
	}
	
	/**
	 * Accessor method for the end year instance variable
	 * @return the user selected end year
	 */
	public int getEndYear() {
		return endYear;
	}
	
	/**
	 * Accessor method for the country instance variable
	 * @return the user selected country for which to fetch data from
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Accessor method for the analysis instance variable
	 * @return the user selected analysis to fetch data for
	 */
	public String getAnalysisType() {
		
		// if the analysis has not yet been initialized, a pop-up is displayed and null is returned
		if(null == analysis) {
			popUpCreator.createPopUp("analysis");
			return null;
		} else {
			return analysis;
		}
	}
	
	/**
	 * Accessor method for the viewerNames instance variable
	 * @return the viewerNames ArrayList as an array of Strings
	 */
	public String[] getViewerNames() {
		// if the viewersNames instance variable is not initialized, or the ArrayList is empty,
		// then a pop-up is displayed and an empty array is returned
		if(null == viewersNames || viewersNames.isEmpty()) {
			popUpCreator.createPopUp("viewers4");
			return new String[0];
		} else {
			return viewersNames.toArray(new String[0]);
		}
	}
	
	/**
	 * Accessor method for the viewersList instance variable
	 * @return the viewersList instance variable as an array of Viewer objects
	 */
	public Viewer[] getViewers() {
		// if the viewersList instance variable is not initialized, or the ArrayList is empty,
		// then a pop-up is displayed and an empty array is returned
		if(null == viewersList || viewersList.isEmpty()) {
			popUpCreator.createPopUp("viewers4");
			return new Viewer[0];
		}
		else {
			return viewersList.toArray(new Viewer[0]);
		}
	}
	
	/**
	 * Method for retrieving list of valid countries for selecting analyses for.
	 * Note: countries in this list may not be valid for some analysis types, but are valid user selections
	 * @return the validCountriesList instance variable
	 */
	public ArrayList<String> getValidCountries() {
		return validCountriesList;
	}
		
	/**
	 * Checks if all the parameters have been set before fetching data
	 * @return true if all parameters have been set, false if otherwise
	 */
	public Boolean check() {
		if(startYear !=0 && endYear !=0 && country != null && analysis != null && viewersList!= null)
			return true;
		else {
			System.out.println(startYear !=0);
			System.out.println(endYear !=0);
			System.out.println(country != null);
			System.out.println(analysis != null);
			System.out.println(viewersList != null);
			popUpCreator.createPopUp("complete selection");
			return false;
		}
	}
	
	/**
	 * Helper method to check if a viewer is invalid
	 * @param newViewer the viewer to be validated
	 * @return true if viewer is invalid, false otherwise
	 */
	private boolean checkViewerInvalid(String newViewer) {
		boolean viewerInvalid = false;
		
		try {
			// CSVReader reads the analysis database storing the list of invalid viewers for each analysis
			CSVReader DBscanner = new CSVReader(new FileReader(analysisDB));
			
			String[] analysisInfo;
			while ((analysisInfo = DBscanner.readNext()) != null && !viewerInvalid) {
				if(this.analysis.equals(analysisInfo[0])) {
					for(String invalidViewer: analysisInfo) {
						// if an invalid viewer for the analysis in the database equals the selected viewer,
						// then viewerInvalid is changed to true
						if(invalidViewer.equals(newViewer)) {
							viewerInvalid = true;
						}
					}
				}
			}
			DBscanner.close();
		// if there is an issue accessing our database, then a pop-up is displayed
		} catch(FileNotFoundException e) {
			popUpCreator.createPopUp("database1");
		} catch (Exception e1) {
			popUpCreator.createPopUp("database2");
			e1.printStackTrace();
		}
		
		// returns true if the viewer is invalid
		return viewerInvalid;	
	}
	
	/**
	 * Helper method for building the list of valid countries, for use in UserRequest and Recalculate
	 */
	private void buildValidCountriesList() {
		try {
			// CSVReader reads the first line of the analysis database
			CSVReader DBscanner = new CSVReader(new FileReader(analysisDB));
						
			String[] tempCountriesList = DBscanner.readNext();
			
			// each element in the first line, after the first element, is added to the valid countries lisst
			for(int i = 1; i < tempCountriesList.length; i++) {
				validCountriesList.add(tempCountriesList[i]);
			}
			
			DBscanner.close();
		// if there is an issue accessing our database, then a pop-up is displayed
		} catch(FileNotFoundException e) {
			popUpCreator.createPopUp("database1");
		} catch (Exception e1) {
			popUpCreator.createPopUp("database2");
			e1.printStackTrace();
		}
	}
	
	/**
	 * Helper method for checking if the selected country is invalid for the analysis type. 
	 * @return true if the country is invalid, false if otherwise
	 */
	private boolean checkCountryInvalid(String newCountry, String newAnalysis) {
		boolean countryInvalid = false;
		
		if(analysisSet || null != this.countryFull) {
			try {
				// CSVReader reads the analysis database storing the list of invalid countries for each analysis
				CSVReader DBscanner = new CSVReader(new FileReader(analysisDB));
				
				String[] analysisInfo;
				while ((analysisInfo = DBscanner.readNext()) != null && !countryInvalid) {
					if(newAnalysis.equals(analysisInfo[0])) {
						for(String invalidCountry: analysisInfo) {
							// if an invalid country for the analysis in the database equals the selected country,
							// then countryInvalid is changed to true
							if(invalidCountry.equals(newCountry)) {
								countryInvalid = true;
							}
						}
					}
				}
				DBscanner.close();
			// if there is an issue accessing our database, then a pop-up is displayed
			} catch(FileNotFoundException e) {
				popUpCreator.createPopUp("database1");
			} catch (Exception e1) {
				popUpCreator.createPopUp("database2");
				e1.printStackTrace();
			}
		}
		// returns true if the country is invalid
		return countryInvalid;	
	}
	
}
