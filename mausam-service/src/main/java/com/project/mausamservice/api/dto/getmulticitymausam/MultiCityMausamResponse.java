package com.project.mausamservice.api.dto.getmulticitymausam;

import java.util.List;

import lombok.Data;

@Data
public class MultiCityMausamResponse {

	List<CityWeather> citiesWeather;
}
