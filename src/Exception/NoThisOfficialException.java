/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content add a nonexistent athlete
 */
public class NoThisOfficialException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoThisOfficialException(String userID) {
		super("This official (" + userID + ") does not existing!");
	}
}
