package cz.martlin.jrest.examples.commons;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class CommonsService {

	private final Random rand = new Random();

	public CommonsService() {
	}

	public Calendar getCurrentDate() {
		return new GregorianCalendar();
	}

	public int getRandomNumber() {
		return rand.nextInt();
	}

	public int getRandomNumber(Integer max) {
		return rand.nextInt(max);
	}

	public int getRandomNumber(Integer min, Integer max) {
		return min + rand.nextInt(max - min);
	}

	public boolean willTomorrowRain() {
		return rand.nextBoolean();
	}

	public WeatherForecast getWeatherForecast() {
		return WeatherForecast.createRandom();
	}

	public void makeTheWorldPeace() {
		throw new UnsupportedOperationException("World peace not supported");
	}
}
