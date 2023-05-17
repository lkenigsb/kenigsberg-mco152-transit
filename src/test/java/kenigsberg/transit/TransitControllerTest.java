package kenigsberg.transit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kenigsberg.transit.json.Distances;
import kenigsberg.transit.json.Extensions;
import kenigsberg.transit.json.MonitoredCall;
import kenigsberg.transit.json.StopMonitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import hu.akarnokd.rxjava3.swing.RxSwingPlugins;
import hu.akarnokd.rxjava3.swing.SwingSchedulers;

import java.util.Dictionary;

class TransitControllerTest {



    @Test
    public void determineProvideInfo()
    {
        // given
        TransitService service = mock();
        JLabel limitedInfo = mock();
        JLabel completeStopInfo = mock();

        TransitController controller = new TransitController(service, limitedInfo, completeStopInfo);

        StopMonitor stopMonitor = mock();
        Observable<StopMonitor> observable = Observable.just(stopMonitor);
        doReturn(observable).when(service).getTransitInfo("404993");

        // when
        controller.determineProvideInfo("404993");

        // then
        //Verify that service method gets called
        verify(service).getTransitInfo("404993");
    }

    @Test
    public void setLimitedTransitInfo()
    {
        // given
        TransitService service = mock();
        JLabel limitedInfo = mock();
        JLabel completeStopInfo = mock();

        TransitController controller = new TransitController(service, limitedInfo, completeStopInfo);

        // when
        controller.setLimitedTransitInfo("00:00:00");

        // then
        verify(limitedInfo).setText("Detailed stop visit not available 00:00:00");
    }

    @Test
    public void setCompleteStopInfo()
    {
        // given
        TransitService service = mock();
        JLabel limitedInfo = mock();
        JLabel completeStopInfo = mock();

        TransitController controller = new TransitController(service, limitedInfo, completeStopInfo);

        MonitoredCall monitoredCall = new MonitoredCall();
        String arrivalTime = monitoredCall.AimedArrivalTime = "00:00:00";
        monitoredCall.Extensions = new Extensions();
        monitoredCall.Extensions.Distances = new Distances();
        String distance  = monitoredCall.Extensions.Distances.PresentableDistance = "0.0 miles away";
        String nameStop = monitoredCall.StopPointName = "Testing Stop";


        // when
        controller.setCompleteStopInfo(monitoredCall);

        // then
        verify(completeStopInfo).setText(nameStop + " Arrival Time: " + arrivalTime + " Distance: " + distance);
    }
}