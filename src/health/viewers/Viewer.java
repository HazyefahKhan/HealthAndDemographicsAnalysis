package health.viewers;

import javax.swing.JComponent;


import health.computeAnalysis.Model;

/**
 * Viewer is the superclass from which all the different types of viewers inherit from.
 * @author Dheeraj Choppara
 *
 */
public abstract class Viewer implements Observer{
	private Model subject;
	private JComponent graph;
	
	/**
	 * @return Model object holding the analysis data
	 */
	public Model getSubject() {
		return subject;
	}
	/**
	 * @param Model object
	 * Stores the Model object attached to the Viewer
	 */
	public void setSubject(Model subject) {
		this.subject = subject;
	}
	
	/**
	 * @return JComponent object which contains the Viewer
	 */
	public JComponent getGraph() {
		return graph;
	}
	/**
	 * @param Jcomponent object holding the Viewer is stored
	 */
	public void setGraph(JComponent graph) {
		this.graph = graph;
	}
	
}
