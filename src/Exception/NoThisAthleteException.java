/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content add a nonexistent athlete
 */
public class NoThisAthleteException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoThisAthleteException(String userID) {
		super("This athlete (" + userID + ") does not existing!");
	}
}
