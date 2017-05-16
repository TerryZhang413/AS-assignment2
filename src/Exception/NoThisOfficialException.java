/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to add an athlete to a wrong type of game e.g assigning
 *          a swimmer to a running game.
 */
public class NoThisOfficialException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoThisOfficialException(String userID) {
		super("This official (" + userID + ") does not existing!");
	}
}
