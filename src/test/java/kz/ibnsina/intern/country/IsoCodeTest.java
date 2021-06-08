package kz.ibnsina.intern.country;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import kz.ibnsina.intern.services.CountryService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class IsoCodeTest {

    @Test
    public void test() throws IOException, GeoIp2Exception {
        CountryService countryService = new CountryService();
        String ipAddress = "79.141.162.81";
        Assert.assertEquals("US", countryService.getIsoCode(ipAddress));
    }
}
