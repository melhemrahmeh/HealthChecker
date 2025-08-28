package com.melhem.healthchecker.dto;

import com.melhem.healthchecker.model.ServiceType;

public class MonitoredServiceDto {
    private Long id;
    private String name;
    private ServiceType type;
    private String address;
    private Integer port;

    public MonitoredServiceDto() {}

    public MonitoredServiceDto(Long id, String name, ServiceType type, String address, Integer port) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.port = port;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ServiceType getType() { return type; }
    public void setType(ServiceType type) { this.type = type; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }
}
