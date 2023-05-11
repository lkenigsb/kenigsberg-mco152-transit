package kenigsberg.transit;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface TransitService {
    @GET("api/siri/stop-monitoring.json?key=8824cb1a-61a5-4a89-827c-d66114bb7f12&OperatorRef=MTA&MonitoringRef=203611&LineRef=MTA%SIM33C")
    Observable<StopMonitor> getTransitInfo();
}
