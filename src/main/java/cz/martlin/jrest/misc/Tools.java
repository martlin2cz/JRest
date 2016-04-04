package cz.martlin.jrest.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Some tools class.
 * 
 * @author martin
 *
 */
public class Tools {

	private static final String SEPARATOR = ", ";

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

	/**
	 * Converts given list of strings to string in format:
	 * 
	 * <pre>
	 * foo{@value #SEPARATOR}bar{@value #SEPARATOR}baz
	 * </pre>
	 * 
	 * @param strings
	 * @return
	 */
	public static String listToString(List<String> strings) {
		Iterator<String> iter = strings.iterator();
		StringBuilder stb = new StringBuilder();

		while (iter.hasNext()) {
			String next = iter.next();
			stb.append(next);
			if (iter.hasNext()) {
				stb.append(SEPARATOR);
			}
		}

		return stb.toString();
	}
}
