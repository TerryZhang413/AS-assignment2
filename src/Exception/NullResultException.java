/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content no result recored can be returned
 */
public class NullResultException extends Exception {

	private static final long serialVersionUID = 1L;

	public NullResultException() {
		super("Do no have any game record now!");
	}
}
