package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class GeoIpServiceTests {

    @Test
    public void testMyIp() {
        String geoIp = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("5.187.75.104");
        assertTrue(geoIp.contains("RU")); //всё верно, это более простой сервис, он возвращает строку, а не объект
    }

    @Test
    public void testInvalidIp() {
        String geoIp = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("5.187.75.xxx");
        assertTrue(geoIp.contains("RU")); //всё верно, это более простой сервис, он возвращает строку, а не объект
    }
}
