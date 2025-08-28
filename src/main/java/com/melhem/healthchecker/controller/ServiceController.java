package com.melhem.healthchecker.controller;

import com.melhem.healthchecker.dto.MonitoredServiceDto;
import com.melhem.healthchecker.dto.ServiceStatusDto;
import com.melhem.healthchecker.model.MonitoredService;
import com.melhem.healthchecker.model.ServiceStatus;
import com.melhem.healthchecker.service.MonitoringService;
import com.melhem.healthchecker.dao.ServiceStatusDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
@Tag(name = "Services", description = "CRUD operations for monitored services")
public class ServiceController {

    private final MonitoringService monitoringService;
    private final ServiceStatusDao statusDao;

    public ServiceController(MonitoringService monitoringService, ServiceStatusDao statusDao) {
        this.monitoringService = monitoringService;
        this.statusDao = statusDao;
    }

    @GetMapping
    @Operation(summary = "List all monitored services")
    public List<MonitoredServiceDto> listServices() {
        return monitoringService.listServices()
                .stream()
                .map(s -> new MonitoredServiceDto(s.getId(), s.getName(), s.getType(), s.getAddress(), s.getPort()))
                .collect(Collectors.toList());
    }

    @PostMapping
    @Operation(summary = "Add a new monitored service")
    public MonitoredServiceDto addService(@RequestBody MonitoredServiceDto dto) {
        MonitoredService service = new MonitoredService(dto.getName(), dto.getType(), dto.getAddress(), dto.getPort());
        MonitoredService saved = monitoringService.addService(service);
        return new MonitoredServiceDto(saved.getId(), saved.getName(), saved.getType(), saved.getAddress(), saved.getPort());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a monitored service by ID")
    public void deleteService(@PathVariable Long id) {
        monitoringService.deleteService(id);
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Get recent health status of a service")
    public List<ServiceStatusDto> getRecentStatus(@PathVariable Long id) {
        List<ServiceStatus> statuses = statusDao.findTop10ByServiceIdOrderByCheckedAtDesc(id);
        return statuses.stream()
                .map(s -> new ServiceStatusDto(s.isHealthy(), s.getMessage(), s.getCheckedAt()))
                .collect(Collectors.toList());
    }
}
