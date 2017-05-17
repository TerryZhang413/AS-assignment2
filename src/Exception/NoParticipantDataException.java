/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content Both DataBase and Text are not readable
 */
public class NoParticipantDataException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoParticipantDataException() {
		super("Both DataBase and Text are not readable!");
	}
}
