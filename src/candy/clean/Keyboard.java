package candy.clean;

import org.jetbrains.annotations.Contract;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Class that reads lines introduced in the console.
 *
 * @author Samuel Castrillo Domínguez
 * @version 1.2.0
 */
public final class Keyboard {

	@Contract(value = " -> fail", pure = true)
	private Keyboard() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Read a line from console.
	 *
	 * @return String written in the command.
	 *
	 * @throws CandyCleanException If there would be any issue with the I/O system.
	 */
	public static String readLine() throws CandyCleanException {
		String line;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			line = br.readLine();
		} catch (Exception e) {
			throw new CandyCleanException("Error: Something went wrong with IO. Please, reenter the input");
		}

		return line;
	}

}
