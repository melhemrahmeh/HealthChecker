package com.melhem.healthchecker.service;

import com.melhem.healthchecker.model.MonitoredService;
import com.melhem.healthchecker.model.ServiceStatus;
import com.melhem.healthchecker.dao.ServiceStatusDao;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;

@Service
public class HealthCheckService {

    private final ServiceStatusDao statusDao;
    private final RestTemplate restTemplate = new RestTemplate();

    public HealthCheckService(ServiceStatusDao statusDao) {
        this.statusDao = statusDao;
    }

    public ServiceStatus check(MonitoredService service) {
        boolean healthy = false;
        String message = "";
        System.out.println("Checking service: " + service.getName() + " [" + service.getType() + "] at " + LocalDateTime.now());

        try {
            switch (service.getType()) {

                case HTTP -> {
                    var response = restTemplate.getForEntity(service.getAddress(), String.class);
                    healthy = response.getStatusCode().is2xxSuccessful();
                    message = "HTTP status: " + response.getStatusCode();
                }

                case TCP -> {
                    try (Socket socket = new Socket(service.getAddress(), service.getPort())) {
                        healthy = true;
                        message = "TCP OK (connected to " + service.getAddress() + ":" + service.getPort() + ")";
                    }
                }

                case DATABASE -> {
                    // Example: JDBC URL with username/password
                    String jdbcUrl = service.getAddress(); // should include user/pass in URL or add extra fields
                    try (Connection conn = DriverManager.getConnection(jdbcUrl)) {
                        healthy = conn.isValid(2);
                        message = healthy ? "DB OK" : "DB connection failed";
                    }
                }
            }
        } catch (Exception e) {
            healthy = false;
            message = "Error: " + e.getMessage();
        }

        System.out.println("Service check result: " + service.getName() + " => " + message);

        ServiceStatus status = new ServiceStatus(service, healthy, message);
        return statusDao.save(status);
    }
}
