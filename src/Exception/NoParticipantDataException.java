/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content when trying to add an athlete to a game which already has 8 athletes
 *          registered
 */
public class NoParticipantDataException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoParticipantDataException() {
		super("Both DataBase and Text are not readable!");
	}
}
