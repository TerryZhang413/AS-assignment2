/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to star a game but no game has been created
 */
public class NoGameCreated extends Exception {

	private static final long serialVersionUID = 1L;

	public NoGameCreated() {
		super("Does not have any Game! Have to creat one first!");
	}
}
