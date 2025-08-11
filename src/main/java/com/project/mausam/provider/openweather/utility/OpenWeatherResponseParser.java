package com.project.mausam.provider.openweather.utility;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.project.mausam.entity.Humidity;
import com.project.mausam.entity.Location;
import com.project.mausam.entity.Mausam;
import com.project.mausam.entity.Temperature;
import com.project.mausam.entity.Visibility;
import com.project.mausam.entity.Weather;
import com.project.mausam.entity.Wind;
import com.project.mausam.provider.openweather.dto.CityOpenWeatherResponse;
import com.project.mausam.provider.openweather.dto.Coord;
import com.project.mausam.provider.openweather.dto.Main;
import com.project.mausam.provider.openweather.dto.OpenWeatherUnits;
import com.project.mausam.provider.openweather.dto.OpenWeatherWindSpeedEnum;
import com.project.mausam.provider.openweather.dto.Sys;

@Component
@Lazy
public class OpenWeatherResponseParser {
	
	public OpenWeatherResponseParser() {
		System.out.println("OpenWeatherResponseParser init.");
	}

	public Mausam parseCityOpenWeatherResponse(final CityOpenWeatherResponse openWeatherResponse, final int units) {

		final Mausam mausam = new Mausam();
		final Location location = new Location();
		final Coord coord = openWeatherResponse.getCoord();
		final Sys sys = openWeatherResponse.getSys();

		location.setCityName(openWeatherResponse.getName());
		location.setLongitude(coord.getLon());
		location.setLatitude(coord.getLat());
		location.setCountry(sys.getCountry());
		mausam.setLocation(location);

		final Weather weather = new Weather();
		final Temperature temperature = new Temperature();
		final Main main = openWeatherResponse.getMain();

		temperature.setCurrent(main.getTemp());
		temperature.setFeelsLike(main.getFeelsLike());
		temperature.setMin(main.getTempMin());
		temperature.setMax(main.getTempMax());
		temperature.setUnit(OpenWeatherUnits.getStandardWeatherUnitFromId(units));
		weather.setTemperature(temperature);

		final Visibility visibility = new Visibility();
		visibility.setValue(openWeatherResponse.getVisibility());
		visibility.setUnit(OpenWeatherConstants.OPENWEATHER_VISIBILITY_UNIT);
		weather.setVisibility(visibility);

		final Wind wind = new Wind();
		final com.project.mausam.provider.openweather.dto.Wind openWeatherWind = openWeatherResponse.getWind();
		wind.setSpeed(openWeatherWind.getSpeed());
		wind.setWindSpeedUnit(OpenWeatherWindSpeedEnum.getWindSpeedUnitfromUnitsId(units));
		weather.setWind(wind);

		final Humidity humidity = new Humidity();
		humidity.setValue(main.getHumidity());
		humidity.setUnit(OpenWeatherConstants.OPENWEATHER_HUMIDITY_UNIT);
		weather.setHumidity(humidity);

		List<com.project.mausam.provider.openweather.dto.Weather> openWeatherWeather = openWeatherResponse.getWeather();
		if(!(null == openWeatherResponse) && openWeatherWeather.size() > 0) {
			com.project.mausam.provider.openweather.dto.Weather details = openWeatherWeather.get(0);
			weather.setWeatherStatement(details.getMain());
			weather.setWeatherDescription(details.getDescription());
		}
		mausam.setWeather(weather);

		return mausam;
	}

}
