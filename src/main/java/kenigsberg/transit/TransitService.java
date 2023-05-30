package kenigsberg.transit;

import dagger.Module;
import io.reactivex.rxjava3.core.Observable;
import kenigsberg.transit.json.StopMonitor;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TransitService {
    @GET("api/siri/stop-monitoring.json?key=8824cb1a-61a5-4a89-827c-" +
            "d66114bb7f12&OperatorRef=MTA&LineRef=MTA%SIM33C")
    Observable<StopMonitor> getTransitInfo(@Query("MonitoringRef") String monitoringRefNum);

    //Ref Num: 203611 (sometimes limited info)
    //Ref Num: 404993
}
