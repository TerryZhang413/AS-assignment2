/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to run a game, which has less than 4 athletes
 *          registered.
 */
public class TooFewAthleteException extends Exception {

	private static final long serialVersionUID = 1L;

	public TooFewAthleteException(String gameID) {
		System.out.println("Game " + gameID + "has not enough athlete");
	}
}
