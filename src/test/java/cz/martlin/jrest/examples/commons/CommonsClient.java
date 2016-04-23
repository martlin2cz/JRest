package cz.martlin.jrest.examples.commons;

import java.util.Calendar;

import cz.martlin.jrest.impl.jarmil.SingleJarmilGuest;
import cz.martlin.jrest.impl.jarmil.handler.Echoer;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilGuestProtocol;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.targets.guest.ObjectOnGuestTarget;
import cz.martlin.jrest.misc.JRestException;

public class CommonsClient {

	private final SingleJarmilGuest guest;

	public CommonsClient() {
		TargetOnGuest target = ObjectOnGuestTarget.create(CommonsServiceApp.NAME);
		JarmilGuestProtocol protocol = new JarmilGuestProtocol(CommonsServiceApp.PORT);
		guest = new SingleJarmilGuest(target, protocol);
	}

	public Calendar getCurrentDate() throws JRestException {
		return guest.invoke("getCurrentDate");
	}

	public int getRandomNumber() throws JRestException {
		return guest.invoke("getRandomNumber");
	}

	public int getRandomNumber(int max) throws JRestException {
		return guest.invoke("getRandomNumber", max);
	}

	public int getRandomNumber(int min, int max) throws JRestException {
		return guest.invoke("getRandomNumber", min, max);
	}

	public boolean willTomorrowRain() throws JRestException {
		return guest.invoke("willTomorrowRain");
	}

	public WeatherForecast getWeatherForecast() throws JRestException {
		return guest.invoke("getWeatherForecast");
	}

	public Exception makeTheWorldPeace() throws JRestException {
		return guest.invoke("makeTheWorldPeace");
	}

	public String invokeEcho() throws JRestException {
		JarmilRequest req = JarmilRequest.createWObjectTarget(Echoer.OBJECT_NAME, Echoer.ECHO_METHOD);
		JarmilResponse resp = guest.sendRequest(req);

		return (String) resp.getData();

	}
}
