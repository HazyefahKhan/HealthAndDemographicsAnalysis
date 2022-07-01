package health.viewers;

import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JScrollPane;

import health.computeAnalysis.Model;
/**
 * This class represents a Viewer of type Report, which is attached to the Model and creates a Text representation of the Model.
 * The report is updated accordingly when changes are made to the model.
 * @author Dheeraj Choppara
 *
 */
public class Report extends Viewer implements Observer {
	
	/**
	 * @param Model object is attached to the specific Viewer
	 */
	public Report(Model subject) {
		this.setSubject(subject); 
		subject.attach(this);
	}
	
	/* 
	 * Viewer is updated according to the new values in the Model
	 * (via the observer pattern)
	 */
	public void update(Subject changedSubject) {
		if (changedSubject.equals(this.getSubject()))
			updateValues();
	}
	
	/**
	 * A report is constructed using the data in the Model
	 * Dynamically creates the report for 1-3 Series data items
	 * JScrollPane object is created with the appropriate data and stored in private attribute JComponent graph;
	 */
	
	private void updateValues() {
		JTextArea report = new JTextArea();
		report.setEditable(false);
		report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		report.setBackground(Color.white);
		
		//Represents the text in the report
		String reportMessage;
		
		//Retrieve data from latest Model
		int numberOfSeries = this.getSubject().getNumOfSeries();
		double[][] values = this.getSubject().getValues();
		int[] years = this.getSubject().getYears();
		
		//Report title
		reportMessage = "==================\n"+ this.getSubject().getAnalysisType() + "==================\n\n";
		
		//Concatenate data for each series
		for(int i=0; i<years.length; i++) {
			reportMessage = reportMessage + "Year " + Integer.toString(years[i])+":\n" + this.getSubject().getSeries1() + " => " + values[0][i] + "\n";
			if (numberOfSeries>1)
				reportMessage = reportMessage + this.getSubject().getSeries2() +" => " + values[1][i] + "\n";
			if (numberOfSeries>2)
				reportMessage = reportMessage + this.getSubject().getSeries3() +" => " + values[2][i] + "\n";
			reportMessage = reportMessage + "\n";
		}
		
		report.setText(reportMessage);
		JScrollPane outputScrollPane = new JScrollPane(report);
		outputScrollPane.setPreferredSize(new Dimension(400, 300));
		
		//JScrollPane stored as an object 
		this.setGraph(outputScrollPane);
	}

}
