package de.telran.controller;

import de.telran.model.CityWeather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebWeatherControllerTest  {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHotCity(){
        CityWeather weather = this.restTemplate.getForObject("http://localhost:" + port + "/weather/berlin", CityWeather.class);
        assertNotNull(weather.getCity());
        assertNotNull(weather.getDate());
        assertEquals("berlin", weather.getCity());
        assertEquals("2019-10-10", weather.getDate());
    }

}
