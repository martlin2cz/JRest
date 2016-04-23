package cz.martlin.jrest.examples.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.JRestException;

public class CommonSimpleClientApp {
	private static final Logger LOG = LoggerFactory.getLogger(CommonsServiceApp.class);

	public static void main(String[] args) throws JRestException {

		LOG.info("Client app starting");
		CommonsClient client = new CommonsClient();
		LOG.info("Client app ready");

		LOG.info("Current datetime: {}", //
				client.getCurrentDate().getTime());

		LOG.info("Random int numbers: {}, {}, {}", //
				client.getRandomNumber(), client.getRandomNumber(), client.getRandomNumber());

		LOG.info("Random int (0-10) numbers: {}, {}, {}", //
				client.getRandomNumber(10), client.getRandomNumber(10), client.getRandomNumber(10));

		LOG.info("Random int (10-15) numbers: {}, {}, {}", //
				client.getRandomNumber(10, 15), client.getRandomNumber(10, 15), client.getRandomNumber(10, 15));

		LOG.info("Will be raining tomorrow? {}  Complete forecast: {}", //
				client.willTomorrowRain(), client.getWeatherForecast());

		try {
			client.makeTheWorldPeace();
		} catch (JRestException e) {
			LOG.error("But making of the world peace will fail", //
					e);
		}

		LOG.info("But we can still echo: {}", //
				client.invokeEcho());

	}
}
