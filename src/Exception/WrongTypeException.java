/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to add an athlete to a wrong type of game e.g assigning
 *          a swimmer to a running game.
 */
public class WrongTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public WrongTypeException(String gameID) {
		System.out.println("This athlete can not join Game " + gameID);
	}
}
