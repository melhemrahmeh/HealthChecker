package com.melhem.healthchecker.service;

import com.melhem.healthchecker.dao.MonitoredServiceDao;
import com.melhem.healthchecker.model.MonitoredService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MonitoringService {

    private final MonitoredServiceDao serviceDao;
    private final HealthCheckService healthCheckService;

    public MonitoringService(MonitoredServiceDao serviceDao, HealthCheckService healthCheckService) {
        this.serviceDao = serviceDao;
        this.healthCheckService = healthCheckService;
    }

    public List<MonitoredService> listServices() {
        return serviceDao.findAll();
    }

    public MonitoredService addService(MonitoredService service) {
        return serviceDao.save(service);
    }

    public void deleteService(Long id) {
        serviceDao.deleteById(id);
    }

    public void runHealthCheck(MonitoredService service) {
        healthCheckService.check(service);
    }
}
