package cz.martlin.jrest.protocol.serializers;

import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;

/**
 * The simplyfying tool for implementation of {@link RequestSerializer} and
 * {@link ResponseSerializer}. This interface extends both of them, so the class
 * will have to implement all four serialization/deserialization methods.
 * 
 * @author martin
 *
 */
public interface ReqRespSerializer extends RequestSerializer, ResponseSerializer {

}
