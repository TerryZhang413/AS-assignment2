/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to add an athlete to a game which already has 8 athletes
 *          registered
 */
public class NoGameCreated extends Exception {

	private static final long serialVersionUID = 1L;

	public NoGameCreated() {
		super("Does not have any Game! Have to creat one first!");
	}
}
