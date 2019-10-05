package de.telran.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class CityWeather {
    private @Id @GeneratedValue Long id;
    String city;
    double temp;
    LocalDate date;

    public CityWeather() {

    }

    public CityWeather(String city, double temp, LocalDate date) {
        this.city = city;
        this.temp = temp;
        this.date = date;
    }

    public CityWeather(String city, double temp) {
        this.city = city;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }
    public double getTemp(){
        return temp;
    }
    public LocalDate getDate() {
        return date;
    }
}
