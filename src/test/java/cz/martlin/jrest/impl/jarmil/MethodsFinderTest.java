package cz.martlin.jrest.impl.jarmil;

import static org.junit.Assert.assertEquals;

import java.awt.Component;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Test;

import cz.martlin.jrest.impl.jarmil.reqresp.MethodsFinder;

public class MethodsFinderTest {
	private final MethodsFinder accessor = new MethodsFinder();

	@Test
	public void testDiffSizes() throws NoSuchMethodException, SecurityException {

		Method methodE = Double.class.getMethod("toString");

		List<Object> args1 = Collections.emptyList();
		Method methodA = accessor.findMethod(Double.class, "toString", args1, true);

		assertEquals(methodE, methodA);
	}

	@Test
	public void testDiffParams() throws NoSuchMethodException, SecurityException {

		Method methodE = JPanel.class.getMethod("add", String.class, Component.class);

		List<Object> args1 = new LinkedList<>();
		args1.add("Text");
		args1.add(new JButton());

		Method methodA = accessor.findMethod(JPanel.class, "add", args1, true);

		assertEquals(methodE, methodA);
	}

	@Test(expected = IllegalStateException.class)
	public void testAmbiguilty() throws NoSuchMethodException, SecurityException {

		Method methodE = StringBuilder.class.getMethod("append", int.class);

		List<Object> args1 = new LinkedList<>();
		args1.add(null);

		Method methodA = accessor.findMethod(StringBuilder.class, "append", args1, true);

		assertEquals(methodE, methodA);
	}
}
