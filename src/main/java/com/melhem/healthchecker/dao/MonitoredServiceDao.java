package com.melhem.healthchecker.dao;

import com.melhem.healthchecker.model.MonitoredService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoredServiceDao extends JpaRepository<MonitoredService, Long> {}
