package de.telran.repository;

import de.telran.model.CityWeather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface WeatherRepository extends JpaRepository<CityWeather, Long> {
    CityWeather findByCity(String city);
    CityWeather findByCityAndDate(String city, LocalDate date);

}
