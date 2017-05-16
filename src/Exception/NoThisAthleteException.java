/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to add an athlete to a wrong type of game e.g assigning
 *          a swimmer to a running game.
 */
public class NoThisAthleteException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoThisAthleteException(String userID) {
		super("This athlete (" + userID + ") does not existing!");
	}
}
