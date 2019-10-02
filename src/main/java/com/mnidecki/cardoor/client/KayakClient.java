package com.mnidecki.cardoor.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnidecki.cardoor.config.KayakConfig;
import com.mnidecki.cardoor.domain.dto.kayak.KayakCarSearchResponeDto;
import com.mnidecki.cardoor.domain.dto.kayak.KayakLocationDto;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class KayakClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(KayakClient.class);

    @Autowired
    private KayakConfig kayakConfig;
    private OkHttpClient client = new OkHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();

    public String getKayakLocationId(String locationName) {

        try {
            Request request = new Request.Builder()
                    .url("https://apidojo-kayak-v1.p.rapidapi.com/locations/search?where="+locationName+"%2C%20%20Poland")
                    .get()
                    .addHeader(kayakConfig.getKayakHeaderHostName(), kayakConfig.getKayakHeaderHostValue())
                    .addHeader(kayakConfig.getKayakHeaderKeyName(), kayakConfig.getKayakHeaderKeyValue())
                    .build();

            ResponseBody responseBody = client.newCall(request).execute().body();
            List<KayakLocationDto> citiesResponse = Arrays.asList(objectMapper.readValue(responseBody.string(), KayakLocationDto[].class));
            LOGGER.info("KAYAK:  location founded" + citiesResponse.toString());
            return  citiesResponse.get(0).getCtid();
        } catch (IOException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }

    }

    public long getKayakAverageTotalCarPrice(String locationName, String startDate, String startHour, String endDate, String endHour) {

        String cityId = getKayakLocationId(locationName);
        int intStartHour = Integer.valueOf(startHour.substring(0,2));
        int intEndHout = Integer.valueOf(endHour.substring(0,2));
        if (!cityId.isEmpty()) {
            try {
                Request request = new Request.Builder()
                        .url(kayakConfig.getKayakCarEndpoint() + "?origincitycode=" + cityId + "&originairportcode=" + locationName + "&pickupdate=" + startDate + "&pickuphour=" + intStartHour + "&dropoffdate=" + endDate + "&dropoffhour=" + intEndHout + "&currency=PLN")
                        .get()
                        .addHeader(kayakConfig.getKayakHeaderHostName(), kayakConfig.getKayakHeaderHostValue())
                        .addHeader(kayakConfig.getKayakHeaderKeyName(), kayakConfig.getKayakHeaderKeyValue())
                        .build();

                    ResponseBody  responseBody = client.newCall(request).execute().body();
                    KayakCarSearchResponeDto carSearchResponse =(objectMapper.readValue(responseBody.string(), KayakCarSearchResponeDto.class));
                    if(carSearchResponse.getCarSet()!=null){
                        double sum = Arrays.stream(carSearchResponse.getCarSet()).mapToDouble(total -> Double.parseDouble(total.getTotalPrice()))
                                .sum();
                        long count = carSearchResponse.getCarSet().length;
                        if(count==0) count++;
                        return (long)sum / count;
                    }

            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage(), e);
                return 0;
            }
        }
        return 0;
    }
}