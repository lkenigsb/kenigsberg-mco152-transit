package kenigsberg.transit;

import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.swing.*;

public class TransitController {
    private TransitService service;
    private JLabel limitedInfo;
    private JLabel stopName;
    private JLabel expectedArrivalTime;

    public TransitController(TransitService service, JLabel limitedInfo, JLabel stopName, JLabel expectedArrivalTime) {
        this.service = service;
        this.limitedInfo = limitedInfo;
        this.stopName = stopName;
        this.expectedArrivalTime = expectedArrivalTime;
    }

    public void determineProvideInfo(String refNum) {
        service.getTransitInfo(refNum)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(
                        stopMonitor -> {
                            if (stopMonitor.Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit == null) {
                                this.setLimitedTransitInfo(refNum);
                            } else {
                                this.setCompleteStopInfo(refNum);
                            }
                        },
                        Throwable::printStackTrace

                );
    }

    public void setLimitedTransitInfo(String refNum) {
        service.getTransitInfo(refNum)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(
                        stopMonitor -> {
                            {
                                String responseTimestamp = stopMonitor.Siri.ServiceDelivery.StopMonitoringDelivery[0].ResponseTimestamp;
                                limitedInfo.setText("Detailed stop visit not available" + responseTimestamp);
                            }

                        }, Throwable::printStackTrace);
    }

    public void setCompleteStopInfo(String refNum) {
        service.getTransitInfo(refNum)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(
                        stopMonitor -> {
                            {
                                String nameStop = stopMonitor.Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].MonitoredVehicleJourney.MonitoredCall.StopPointName;
                                String arrivalTime = stopMonitor.Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].MonitoredVehicleJourney.MonitoredCall.ExpectedArrivalTime;
                                stopName.setText(nameStop);
                                expectedArrivalTime.setText(arrivalTime);
                            }

                        }, Throwable::printStackTrace);
    }
}