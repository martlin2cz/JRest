package cz.martlin.jrest.examples.commons;

import java.util.Calendar;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.impl.jarmil.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.handlers.Echoer;
import cz.martlin.jrest.impl.jarmil.handlers.JarmilHandler;
import cz.martlin.jrest.misc.JRestException;

public class CommonsClient {

	private final JRestGuest<JarmilRequest, JarmilResponse> guest;

	public CommonsClient() {
		guest = new JRestGuest<>(CommonsServiceApp.PROTOCOL);
	}

	public Calendar getCurrentDate() throws JRestException {
		JarmilRequest req = JarmilRequest.create(CommonsService.class, CommonsService.NAME, "getCurrentDate");
		JarmilResponse resp = guest.sendRequest(req);

		return (Calendar) resp.getData();
	}

	public int getRandomNumber() throws JRestException {
		JarmilRequest req = JarmilRequest.create(CommonsService.class, CommonsService.NAME, "getRandomNumber");
		JarmilResponse resp = guest.sendRequest(req);

		return (Integer) resp.getData();
	}

	public int getRandomNumber(int max) throws JRestException {
		JarmilRequest req = JarmilRequest.create(CommonsService.class, CommonsService.NAME, "getRandomNumber", 10);
		JarmilResponse resp = guest.sendRequest(req);

		return (Integer) resp.getData();
	}

	public boolean willTomorrowRain() throws JRestException {
		JarmilRequest req = JarmilRequest.create(CommonsService.class, CommonsService.NAME, "willTomorrowRain");
		JarmilResponse resp = guest.sendRequest(req);

		return (Boolean) resp.getData();
	}

	public WeatherForecast getWeatherForecast() throws JRestException {
		JarmilRequest req = JarmilRequest.create(CommonsService.class, CommonsService.NAME, "getWeatherForecast");
		JarmilResponse resp = guest.sendRequest(req);

		return (WeatherForecast) resp.getData();
	}

	public Exception makeTheWorldPeace() throws JRestException {
		JarmilRequest req = JarmilRequest.create(CommonsService.class, CommonsService.NAME, "makeTheWorldPeace");
		JarmilResponse resp = guest.sendRequest(req);

		return (Exception) resp.getData();
	}

	public String invokeEcho() throws JRestException {
		JarmilRequest req = JarmilRequest.create(Echoer.class, JarmilHandler.ECHOER_NAME, Echoer.ECHO_METHOD);
		JarmilResponse resp = guest.sendRequest(req);

		return (String) resp.getData();

	}
}
