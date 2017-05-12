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

	public TooFewAthleteException() {
		System.out.println("Game has not enough athlete");
	}
}
