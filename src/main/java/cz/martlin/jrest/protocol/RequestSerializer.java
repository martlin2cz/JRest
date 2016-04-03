package cz.martlin.jrest.protocol;

import cz.martlin.jrest.misc.JRestException;

public interface RequestSerializer {

	public String serializeRequest(JRestRequest request) throws JRestException;

	public JRestRequest deserializeRequest(String serialized) throws JRestException;

}
