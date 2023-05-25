package kenigsberg.transit;

import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import kenigsberg.transit.json.MonitoredCall;
import kenigsberg.transit.json.MonitoredStopVisit;
import kenigsberg.transit.json.StopMonitor;

import java.awt.*;

public class TransitController {
    private TransitService service;
    private JLabel limitedInfo;
    private DefaultTableModel tableModel;



    public TransitController(TransitService service, JLabel limitedInfo, DefaultTableModel tableModel) {
        this.service = service;
        this.limitedInfo = limitedInfo;
        this.tableModel = tableModel;
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

        for (int i = 0; i < monitoredStopVisits.length; i++) {
            String nameStop = monitoredStopVisits[i].MonitoredVehicleJourney.MonitoredCall.StopPointName;
            String arrivalTime = monitoredStopVisits[i].MonitoredVehicleJourney.MonitoredCall.AimedArrivalTime;
            String distance = monitoredStopVisits[i].MonitoredVehicleJourney.MonitoredCall.Extensions.Distances.PresentableDistance;

            String[] row = {nameStop, arrivalTime, distance};
            tableModel.addRow(row);
        }
    }
}