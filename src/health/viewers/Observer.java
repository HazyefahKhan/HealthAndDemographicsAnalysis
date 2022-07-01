package health.viewers;
import java.util.ArrayList;
/**
 * This class is the interface used by the Viewers which implements the observer pattern.
 * 
 * @author Dheeraj Choppara
 *
 */
public interface Observer {
	
	/**
	 * @param Observer updated according to Subject
	 */
	public void update(Subject subject);
}
