package cz.martlin.jrest.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Some tools class.
 * 
 * @author martin
 *
 */
public class Tools {

	/**
	 * Reads given input stream (with characters in default encoding) into
	 * String.
	 * 
	 * @param ins
	 * @return
	 * @throws JRestException
	 */
	public static String read(InputStream ins) throws JRestException {
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new JRestException("Cannot read input", e);
		}
	}

	/**
	 * Writes given string to given output stream in default encoding.
	 * 
	 * @param ous
	 * @param string
	 * @throws JRestException
	 */
	public static void write(OutputStream ous, String string) throws JRestException {
		PrintWriter pw = new PrintWriter(ous, true);
		pw.println(string);

		if (pw.checkError()) {
			throw new JRestException("Cannot write output");
		}
	}
}
