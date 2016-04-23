package cz.martlin.jrest.impl.jarmil.target;

import java.io.Serializable;

public interface TargetOfInvocation extends Serializable {

	public String getIdentifier();
	
	public TargetType getType();

}
