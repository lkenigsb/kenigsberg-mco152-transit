package kenigsberg.transit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.jupiter.api.Assertions.*;

class TransitServiceTest {
    @Test
    public void getTransitInfo() {
        // given
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bustime.mta.info//")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();


        TransitService service = retrofit.create(TransitService.class);

        // when
        StopMonitor stopMonitor = service.getTransitInfo().blockingFirst();

        // then
        Assertions.assertNotNull(stopMonitor);
        Assertions.assertNotNull(stopMonitor.ServiceDelivery.StopMonitoringDeliveries[0].MonitoredStopVisits[0].MonitoredVehicleJourney.DestinationName);
        //Below test will fail when weather is cold
        //Assertions.assertTrue(currentWeather.main.temp > 0);
        //Assertions.assertEquals("Staten Island", currentWeather.name);
    }


}