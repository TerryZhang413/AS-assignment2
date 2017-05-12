/**
 * 
 */
package Exception;

/**
 * @author Yipeng Zhang
 * @content choose a nonexistent game type
 */
public class OutOfGameType extends Exception {

	private static final long serialVersionUID = 1L;

	public OutOfGameType() {
		System.out.println("Nonexistent game type!");
	}
}
