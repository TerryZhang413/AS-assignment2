/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to add an athlete to a game which already has 8 athletes
 *          registered
 */
public class GameFullException extends Exception {

	private static final long serialVersionUID = 1L;

	public GameFullException(String gameID) {
		System.out.println("Game " + gameID + " has too many athlete");
	}
}
