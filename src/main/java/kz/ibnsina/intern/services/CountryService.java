package kz.ibnsina.intern.services;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CountryService {
    private static String DB_PATH = "C:\\Users\\IBNSINA\\IdeaProjects\\intern\\country\\GeoLite2-Country.mmdb";
    private DatabaseReader database;

    public CountryService() throws IOException {
        File file = new File(DB_PATH);
        this.database = new DatabaseReader.Builder(file).build();
    }

    public List<kz.ibnsina.intern.dtos.response.CountryResponse>  getAll() {
        String[] countryCodes = Locale.getISOCountries();
        List<kz.ibnsina.intern.dtos.response.CountryResponse> countryResponses = new ArrayList<>();
        for (String countryCode : countryCodes) {

            Locale obj = new Locale("", countryCode);
            countryResponses.add(new kz.ibnsina.intern.dtos.response.CountryResponse(obj.getCountry(), obj.getDisplayCountry()));
        }
        return countryResponses;
    }

    public String getIsoCode(String ipAddress) throws IOException, GeoIp2Exception {
        InetAddress ipA = InetAddress.getByName(ipAddress);
        CountryResponse countryResponse = database.country(ipA);
        return countryResponse.getCountry().getIsoCode();
    }

    public String getCountryName(String ipAddress) throws IOException, GeoIp2Exception {
        InetAddress ipA = InetAddress.getByName(ipAddress);
        CountryResponse countryResponse = database.country(ipA);
        return countryResponse.getCountry().getName();
    }
}
