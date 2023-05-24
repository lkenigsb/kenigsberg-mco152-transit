package kenigsberg.transit;

import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.swing.*;

import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import kenigsberg.transit.json.MonitoredCall;
import kenigsberg.transit.json.MonitoredStopVisit;
import kenigsberg.transit.json.StopMonitor;

import java.awt.*;

public class TransitController {
    private TransitService service;
    private JLabel limitedInfo;
    private JLabel completeStopInfo;
    private JPanel detailStopPanel;

    public TransitController(TransitService service, JLabel limitedInfo, JPanel detailStopPanel) {
        this.service = service;
        this.limitedInfo = limitedInfo;
        this.detailStopPanel = detailStopPanel;
    }

    public void determineProvideInfo(String refNum) {
        service.getTransitInfo(refNum)
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        stopMonitor -> {
                            if (stopMonitor.Siri.ServiceDelivery.
                                    StopMonitoringDelivery[0].MonitoredStopVisit == null) {
                                setLimitedTransitInfo(stopMonitor.Siri.ServiceDelivery.
                                        StopMonitoringDelivery[0].ResponseTimestamp);
                            } else {
                                setCompleteStopInfo(stopMonitor.Siri.ServiceDelivery.StopMonitoringDelivery[0].
                                        MonitoredStopVisit
                                );
                            }
                        },
                        Throwable::printStackTrace
                );
    }

    public void setLimitedTransitInfo(String responseTimeStamp) {
        limitedInfo.setText("Detailed stop visit not available " + responseTimeStamp);
    }

    public void setCompleteStopInfo(MonitoredStopVisit[] monitoredStopVisits) {

        JLabel title = new JLabel("List of Stops:");
        detailStopPanel.add(title, Component.CENTER_ALIGNMENT);

        for (int i = 0; i < monitoredStopVisits.length; i++) {
            String nameStop = monitoredStopVisits[i].MonitoredVehicleJourney.MonitoredCall.StopPointName;
            String arrivalTime = monitoredStopVisits[i].MonitoredVehicleJourney.MonitoredCall.AimedArrivalTime;
            String distance = monitoredStopVisits[i].MonitoredVehicleJourney.MonitoredCall.Extensions.Distances.PresentableDistance;

            detailStopPanel.add(new JLabel(" "), Component.CENTER_ALIGNMENT);
            JLabel label = new JLabel();
            label.setText(nameStop + " Arrival Time: " + arrivalTime + " Distance: " + distance);
            detailStopPanel.add(label, Component.CENTER_ALIGNMENT);

        }

        /*String nameStop = monitoredCall.StopPointName;
        String arrivalTime = monitoredCall.AimedArrivalTime;
        String distance = monitoredCall.Extensions.Distances.PresentableDistance;
        completeStopInfo.setText(nameStop + " Arrival Time: " + arrivalTime + " Distance: " + distance);*/

    }
}