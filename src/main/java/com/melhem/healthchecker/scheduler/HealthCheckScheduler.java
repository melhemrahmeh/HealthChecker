package com.melhem.healthchecker.scheduler;

import com.melhem.healthchecker.model.MonitoredService;
import com.melhem.healthchecker.service.MonitoringService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckScheduler {

    private final MonitoringService monitoringService;

    public HealthCheckScheduler(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    // every 60 seconds
    @Scheduled(fixedRate = 60000)
    public void checkAllServices() {
        for (MonitoredService service : monitoringService.listServices()) {
            monitoringService.runHealthCheck(service);
        }
    }
}
