package kz.ibnsina.intern.country;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import kz.ibnsina.intern.services.CountryService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CountryNameTest {

    @Test
    public void test() throws IOException, GeoIp2Exception {
        CountryService countryService = new CountryService();
        Assert.assertEquals("Kazakhstan", countryService.getCountryName("37.150.166.191"));
    }
}
