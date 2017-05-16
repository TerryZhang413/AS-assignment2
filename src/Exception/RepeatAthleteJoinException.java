/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to add an athlete already joining this game
 */
public class RepeatAthleteJoinException extends Exception {

	private static final long serialVersionUID = 1L;

	public RepeatAthleteJoinException(String userID) {
		super("This athlete (" + userID + ") already join this game!");
	}
}
