package com.melhem.healthchecker.scheduler;

import com.melhem.healthchecker.model.MonitoredService;
import com.melhem.healthchecker.service.HealthCheckService;
import com.melhem.healthchecker.service.MonitoringService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HealthCheckScheduler {

    private static final Logger logger = LogManager.getLogger(HealthCheckScheduler.class);

    private final MonitoringService monitoringService;
    private final HealthCheckService healthCheckService;

    public HealthCheckScheduler(MonitoringService monitoringService, HealthCheckService healthCheckService) {
        this.monitoringService = monitoringService;
        this.healthCheckService = healthCheckService;
    }

    /**
     * Scheduled health check runs every 60 seconds.
     */
    @Scheduled(fixedRate = 60000)
    public void checkAllServices() {
        List<MonitoredService> services = monitoringService.listServices();

        if (services.isEmpty()) {
            logger.info("No services to monitor at this time.");
            return;
        }

        logger.info("Starting scheduled health checks for {} services.", services.size());

        for (MonitoredService service : services) {
            try {
                healthCheckService.check(service);
            } catch (Exception e) {
                logger.error("Health check failed for service {}: {}", service.getName(), e.getMessage(), e);
            }
        }

        logger.info("Scheduled health checks completed.");
    }
}
