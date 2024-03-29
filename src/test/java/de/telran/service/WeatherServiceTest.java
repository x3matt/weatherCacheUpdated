package de.telran.service;

import de.telran.model.CityWeather;
import de.telran.model.ConsolidatedWeather;
import de.telran.model.WeatherForecast;
import de.telran.repository.WeatherRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class WeatherServiceTest {
    @MockBean
    private ExternalWeatherService externalWeatherService;
    @MockBean
    private WeatherRepository repository;

    WeatherService service;

    @Before
    public void setUp(){service = new WeatherService(externalWeatherService,repository);}

    @Test
    public void testWeatherIsInRepository() throws Exception{
        String city = "berlin";
        when(repository.findByCity(city)).thenReturn(createCityWeather());

        CityWeather weather = service.getWeatherByCityName(city);
        assertNotNull("by city name",weather);
        assertEquals("city name",city,weather.getCity());
        assertEquals("temp",12.5,weather.getTemp(),0.1);

        verify(repository,times(1)).findByCity(city);
        verify(externalWeatherService,never()).getWeatherForCity(any());
    }
    @Test
    public void testWeatherIsNotInRepository() throws Exception{
        String city = "berlin";
        when(repository.findByCity(city)).thenReturn(null);
        when(externalWeatherService.getWeatherForCity(city)).thenReturn(createWeatherForecast());
        CityWeather weatherByCityName = service.getWeatherByCityName(city);
        assertNotNull("by city name",weatherByCityName);
        assertEquals("city","berlin",weatherByCityName.getCity());
        assertEquals("temp",12.5,weatherByCityName.getTemp(),0.1);

        verify(repository,times(1)).findByCity(city);
        verify(externalWeatherService,times(1)).getWeatherForCity(city);
        verify(repository,times(1)).save(any());
    }

    private CityWeather createCityWeather(){
        return new CityWeather("berlin",12.5);
    }
    private WeatherForecast createWeatherForecast(){
        ConsolidatedWeather c = new ConsolidatedWeather();
        c.setTheTemp(12.5);
        ConsolidatedWeather[] cArray = {c};
        WeatherForecast w = new WeatherForecast();
        w.setTitle("berlin");
        w.setConsolidatedWeather(cArray);
        return w;
    }
}
