/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to add an athlete to a game which already has 8 athletes
 *          registered
 */
public class NullResultException extends Exception {

	private static final long serialVersionUID = 1L;

	public NullResultException() {
		super("Do no have any game record now!");
	}
}
