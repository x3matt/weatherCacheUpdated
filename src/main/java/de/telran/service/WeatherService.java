package de.telran.service;

import de.telran.exception.CityNotFoundException;
import de.telran.model.CityWeather;
import de.telran.model.WeatherForecast;
import de.telran.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class WeatherService {
    private ExternalWeatherService externalWeatherService;
    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(ExternalWeatherService externalWeatherService,
                          WeatherRepository repository) {
        this.externalWeatherService = externalWeatherService;
        this.weatherRepository = repository;
    }

    public CityWeather getWeatherByCityName(String city) throws CityNotFoundException {
        CityWeather weather = weatherRepository.findByCity(city);
        if(weather == null){
            weather = createCityWeather(externalWeatherService.getWeatherForCity(city));
            weatherRepository.save(weather);
            return weather;
        }
        return weather;
    }
    public CityWeather getWeatherByCityName(String city, LocalDate date) throws CityNotFoundException {
        CityWeather weather = weatherRepository.findByCityAndDate(city,date);
        if(weather == null){
            weather = createCityWeather(externalWeatherService.getWeatherForCity(city,date));
            weatherRepository.save(weather);
            return weather;
        }
        return weather;
    }

    private CityWeather createCityWeather(WeatherForecast forecast){
        return new CityWeather(forecast.getTitle().toLowerCase(), forecast.getTemp());
    }
}
