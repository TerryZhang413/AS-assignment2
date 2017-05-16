/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to add an athlete to a game which already has 8 athletes
 *          registered
 */
public class NoneDBConnectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoneDBConnectionException() {
		super("Cannot connect to DataBase! DataBase does not existing or has been occupied!");
	}
}
