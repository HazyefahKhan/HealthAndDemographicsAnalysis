package health.viewers;
import java.util.ArrayList;

/**
 * Subject is the superclass of the Model which holds the list of Observers(implemented as Viewers).
 * The class automatically attaches, detaches and updates the list of Observers when changes are made to the model. 
 * @author Dheeraj Choppara
 *
 */
public abstract class Subject {
	private ArrayList<Observer> observers = new ArrayList<>();
	
	/**
	 * @param Observer object is stored in the list of active viewers
	 * and will be updated when changes are made to the Subjects
	 */
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	/**
	 * @param Observer object is removed from the list of active viewers
	 * and will no longer be updated when changes are made to the Subject
	 */
	public void detach(Observer observer) {
		observers.remove(observer);
	}
	
	/**
	 * All active Observers will be updated according to changes made to Subject
	 */
	public void notifyObservers() {
		for (Observer observer : observers)
			observer.update(this);
	}
}
