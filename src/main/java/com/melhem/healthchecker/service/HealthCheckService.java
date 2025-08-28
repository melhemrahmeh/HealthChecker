package com.melhem.healthchecker.service;

import com.melhem.healthchecker.model.MonitoredService;
import com.melhem.healthchecker.model.ServiceStatus;
import com.melhem.healthchecker.dao.ServiceStatusDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;

@Service
public class HealthCheckService {

    private static final Logger logger = LogManager.getLogger(HealthCheckService.class);

    private final ServiceStatusDao statusDao;
    private final RestTemplate restTemplate = new RestTemplate();

    public HealthCheckService(ServiceStatusDao statusDao) {
        this.statusDao = statusDao;
    }

    public ServiceStatus check(MonitoredService service) {
        boolean healthy = false;
        String message = "";
        logger.info("Checking service: {} [{}] at {}", service.getName(), service.getType(), LocalDateTime.now());

        try {
            switch (service.getType()) {
                case HTTP -> {
                    var response = restTemplate.getForEntity(service.getAddress(), String.class);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        healthy = true;
                        message = "HTTP OK, status: " + response.getStatusCode();
                    } else {
                        healthy = false;
                        message = "HTTP FAILED, status: " + response.getStatusCode();
                    }
                }

                case TCP -> {
                    try (Socket socket = new Socket(service.getAddress(), service.getPort())) {
                        healthy = true;
                        message = "TCP OK (connected to " + service.getAddress() + ":" + service.getPort() + ")";
                    } catch (Exception e) {
                        healthy = false;
                        message = "TCP FAILED: " + e.getMessage();
                    }
                }

                case DATABASE -> {
                    try (Connection conn = DriverManager.getConnection(service.getAddress())) {
                        if (conn.isValid(2)) {
                            healthy = true;
                            message = "DB OK";
                        } else {
                            healthy = false;
                            message = "DB connection failed";
                        }
                    } catch (Exception e) {
                        healthy = false;
                        message = "DB ERROR: " + e.getMessage();
                    }
                }

                default -> {
                    healthy = false;
                    message = "Unknown service type";
                }
            }
        } catch (Exception e) {
            healthy = false;
            message = "Unexpected error: " + e.getMessage();
        }

        logger.info("Service check result: {} => {}", service.getName(), message);

        ServiceStatus status = new ServiceStatus(service, healthy, message);
        return statusDao.save(status);
    }
}