package candy.clean;

/**
 * Class that represents the exceptions thrown during the game.
 *
 * @author Samuel Castrillo Domínguez
 * @version 1.1.0
 */

public class CandyCleanException extends Exception {

	/**
	 * Serial for CandyCleanException
	 */
	private static final long serialVersionUID = -7140475513325365909L;

	public CandyCleanException(String e) {
		super(e);
	}
}
