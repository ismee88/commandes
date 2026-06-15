package com.polytech.commandes.repository;

import com.polytech.commandes.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository
        extends JpaRepository<RequestLog, Long> {
}
