package cz.martlin.jrest.protocol;

import cz.martlin.jrest.misc.JRestException;

public interface ResponseSerializer {
	public String serializeResponse(JRestResponse response) throws JRestException;

	public JRestResponse deserializeResponse(String serialized) throws JRestException;

}
