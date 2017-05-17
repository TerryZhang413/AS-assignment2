/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content Cannot connect to DataBase, DataBase does not existing or has been
 *          occupied
 */
public class NoneDBConnectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoneDBConnectionException() {
		super("Cannot connect to DataBase! DataBase does not existing or has been occupied!");
	}
}
