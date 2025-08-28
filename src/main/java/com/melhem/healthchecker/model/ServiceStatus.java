package com.melhem.healthchecker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service_statuses")
public class ServiceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean healthy;

    private String message;

    private LocalDateTime checkedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private MonitoredService service;

    // Convenience constructor
    public ServiceStatus(MonitoredService service, boolean healthy, String message) {
        this.service = service;
        this.healthy = healthy;
        this.message = message;
        this.checkedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ServiceStatus{" +
                "id=" + id +
                ", healthy=" + healthy +
                ", message='" + message + '\'' +
                ", checkedAt=" + checkedAt +
                '}';
    }
}
