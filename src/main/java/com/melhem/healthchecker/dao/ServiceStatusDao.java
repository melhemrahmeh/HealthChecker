package com.melhem.healthchecker.dao;

import com.melhem.healthchecker.model.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceStatusDao extends JpaRepository<ServiceStatus, Long> {
    List<ServiceStatus> findTop10ByServiceIdOrderByCheckedAtDesc(Long serviceId);
}
