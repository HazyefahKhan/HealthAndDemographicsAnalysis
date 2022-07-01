package health.analysisComputations;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import health.computeAnalysis.Data;
import health.computeAnalysis.UserRequest;


/**
 * FetchData is responsible for making the call to the World Bank API based off of Usre's input from System UI
 * collects and stores the data in a Data object
 * @author Hazyefah Khan
 */
public class FetchData {
	private Data data;
	
	/**
	 * The constructor for the class that makes the API call
	 * @param request the UserRequest object with the parameters for the data retrieval
	 * @param analysisID the ID of the analysis to be retrieved
	 * @return data object with data from WorldBankAPI
	 */
	public FetchData(UserRequest request, String analysisID) {
		// Call to WorldBankAPI
		String urlString = String.format(
				"http://api.worldbank.org/v2/country/%s/indicator/" + analysisID + "?date=" + request.getStartYear() + ":" + request.getEndYear() +
				"&format=json", request.getCountry());

		// Data object to hold World Bank API data
		Data data = new Data();

		// Double to hold values from WorldBankAPI
		double value = 0;
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				// Array to hold years ranging from User's start year and end year
				int years[] = new int[jsonArray.get(1).getAsJsonArray().size()];
				// Array to hold values retrieved from WorldBankAPI
				double values[] = new double[jsonArray.get(1).getAsJsonArray().size()];
				int year;
				// Loop through JSON array from WorldBankAPI and put those years into year array
				// put values into values array
				for (int i = 0; i < sizeOfResults; i++) {
					year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
					years[i] = year;
					if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull()) {
						values[i] = 0;
					}
					else {
						value = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value")
								.getAsDouble();

						values[i] = value;
					}
				}
				// Store data collected into data object
				data.setAnalysisID(analysisID);
				data.setValues(values);
				data.setYears(years);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}

		// Return data object with data from WorldBankAPI
		this.data = data;
	}
	
	/**
	 * Accessor method for the data instance variable
	 * @return returns the retrieved data from the API
	 */
	public Data getData() {
		return data;
	}
	
	

}
