/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying run a game which has no official appointed.
 */
public class NoRefereeException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoRefereeException() {
		System.out.println("Game do not have a official");
	}
}
