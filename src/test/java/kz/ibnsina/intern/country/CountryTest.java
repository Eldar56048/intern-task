package kz.ibnsina.intern.country;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import kz.ibnsina.intern.services.CountryService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CountryTest {
    @Test
    public void countryGetAllTest() throws IOException, GeoIp2Exception {
        CountryService countryService = new CountryService();
        Assert.assertNotNull("Country get all test failed",countryService.getAll());
        Assert.assertEquals("Countries list test failed",249, countryService.getAll().size());
    }

    @Test
    public void countryNameTest() throws IOException, GeoIp2Exception {
        CountryService countryService = new CountryService();
        Assert.assertEquals("Country name test failed","Kazakhstan", countryService.getCountryName("37.150.166.191"));
    }

    @Test
    public void isoCodeTest() throws IOException, GeoIp2Exception {
        CountryService countryService = new CountryService();
        String ipAddress = "79.141.162.81";
        Assert.assertEquals("Iso code test failed","US", countryService.getIsoCode(ipAddress));
    }
}
