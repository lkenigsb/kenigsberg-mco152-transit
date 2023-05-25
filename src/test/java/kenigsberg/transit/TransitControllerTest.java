package kenigsberg.transit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kenigsberg.transit.json.*;
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
    public void determineProvideInfo() {
        // given
        TransitService service = mock();
        JLabel limitedInfo = mock();
        DefaultListModel listModel = mock();

        TransitController controller = new TransitController(service, limitedInfo, listModel);

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
    public void setLimitedTransitInfo() {
        // given
        TransitService service = mock();
        JLabel limitedInfo = mock();
        DefaultListModel listModel = mock();

        TransitController controller = new TransitController(service, limitedInfo, listModel);

        // when
        controller.setLimitedTransitInfo("00:00:00");

        // then
        verify(limitedInfo).setText("Detailed stop visit not available 00:00:00");
    }

    @Test
    public void setCompleteStopInfo() {
        // given
        TransitService service = mock();
        JLabel limitedInfo = mock();
        DefaultListModel listModel = mock();

        TransitController controller = new TransitController(service, limitedInfo, listModel);

        MonitoredStopVisit[] monitoredStopVisits = new MonitoredStopVisit[1];
        monitoredStopVisits[0] = new MonitoredStopVisit();
        monitoredStopVisits[0].MonitoredVehicleJourney = new MonitoredVehicleJourney();
        monitoredStopVisits[0].MonitoredVehicleJourney.MonitoredCall = new MonitoredCall();
        String arrivalTime = monitoredStopVisits[0].MonitoredVehicleJourney.MonitoredCall.AimedArrivalTime = "00:00:00";
        monitoredStopVisits[0].MonitoredVehicleJourney.MonitoredCall.Extensions = new Extensions();
        monitoredStopVisits[0].MonitoredVehicleJourney.MonitoredCall.Extensions.Distances = new Distances();
        String distance = monitoredStopVisits[0].MonitoredVehicleJourney.MonitoredCall.Extensions.Distances.PresentableDistance = "0.0 miles away";
        String nameStop = monitoredStopVisits[0].MonitoredVehicleJourney.MonitoredCall.StopPointName = "Testing Stop";


        // when
        controller.setCompleteStopInfo(monitoredStopVisits);

        // then
        verify(listModel).addElement(nameStop + " Arrival Time: " + arrivalTime + " Distance: " + distance);
    }
}