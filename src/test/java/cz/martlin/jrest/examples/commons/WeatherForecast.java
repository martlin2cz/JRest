package cz.martlin.jrest.examples.commons;

import java.io.Serializable;
import java.util.Random;

public class WeatherForecast implements Serializable {

	private static final long serialVersionUID = 7328854674018219803L;

	private final String weather;
	private final int temperature;
	private final int wind;

	public WeatherForecast(String weather, int temperature, int wind) {
		super();
		this.weather = weather;
		this.temperature = temperature;
		this.wind = wind;
	}

	public String getWeather() {
		return weather;
	}

	public int getTemperature() {
		return temperature;
	}

	public int getWind() {
		return wind;
	}

	@Override
	public String toString() {
		return "WeatherForecast [weather=" + weather + ", temperature=" + temperature + ", wind=" + wind + "]";
	}

	public static WeatherForecast createRandom() {
		Random rand = new Random();
		String weather = null;
		switch (rand.nextInt(3)) {
		case 0:
			weather = "Sunny";
			break;
		case 1:
			weather = "Cloudy";
			break;
		case 2:
			weather = "Raining";
			break;

		}

		int temperature = 10 + rand.nextInt(30);
		int wind = rand.nextInt(35);

		return new WeatherForecast(weather, temperature, wind);
	}

}
