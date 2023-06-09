package kenigsberg.transit;

import kenigsberg.transit.json.StopMonitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class TransitServiceTest {
    @Test
    public void getTransitInfo() {
        // given
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bustime.mta.info/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();


        TransitService service = retrofit.create(TransitService.class);

        // when
        StopMonitor stopMonitor = service.getTransitInfo("404993").blockingFirst();

        // then
        Assertions.assertNotNull(stopMonitor);
        Assertions.assertNotNull(stopMonitor.Siri.ServiceDelivery.StopMonitoringDelivery[0].ResponseTimestamp);
        Assertions.assertNotNull(stopMonitor.Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].MonitoredVehicleJourney.DestinationName);
    }
}