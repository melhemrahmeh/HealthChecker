package com.melhem.healthchecker.dto;

import java.time.LocalDateTime;

public class ServiceStatusDto {
    private boolean healthy;
    private String message;
    private LocalDateTime checkedAt;

    public ServiceStatusDto() {}

    public ServiceStatusDto(boolean healthy, String message, LocalDateTime checkedAt) {
        this.healthy = healthy;
        this.message = message;
        this.checkedAt = checkedAt;
    }

    // getters and setters
    public boolean isHealthy() { return healthy; }
    public void setHealthy(boolean healthy) { this.healthy = healthy; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCheckedAt() { return checkedAt; }
    public void setCheckedAt(LocalDateTime checkedAt) { this.checkedAt = checkedAt; }
}
