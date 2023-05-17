package kenigsberg.transit;

import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.swing.*;

import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import kenigsberg.transit.json.MonitoredCall;
import kenigsberg.transit.json.StopMonitor;

public class TransitController {
    private TransitService service;
    private JLabel limitedInfo;
    private JLabel completeStopInfo;

    public TransitController(TransitService service, JLabel limitedInfo,
                             JLabel completeStopInfo) {
        this.service = service;
        this.limitedInfo = limitedInfo;
        this.completeStopInfo = completeStopInfo;
    }

    public void determineProvideInfo(String refNum) {
        service.getTransitInfo(refNum)
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        stopMonitor -> {
                            if (stopMonitor.Siri.ServiceDelivery.
                                    StopMonitoringDelivery[0].MonitoredStopVisit == null)
                            {
                                setLimitedTransitInfo(stopMonitor.Siri.ServiceDelivery.
                                        StopMonitoringDelivery[0].ResponseTimestamp);
                            } else
                            {
                                setCompleteStopInfo(stopMonitor.Siri.ServiceDelivery.StopMonitoringDelivery[0].
                                        MonitoredStopVisit[0].MonitoredVehicleJourney.MonitoredCall
                                );
                            }
                        },
                        Throwable::printStackTrace
                );
    }

    public void setLimitedTransitInfo(String responseTimeStamp) {
        limitedInfo.setText("Detailed stop visit not available " + responseTimeStamp);
    }

    public void setCompleteStopInfo(MonitoredCall monitoredCall) {
        String nameStop = monitoredCall.StopPointName;
        String arrivalTime = monitoredCall.AimedArrivalTime;
        String distance = monitoredCall.Extensions.Distances.PresentableDistance;
        completeStopInfo.setText(nameStop + " Arrival Time: " + arrivalTime + " Distance: " + distance);

    }
}