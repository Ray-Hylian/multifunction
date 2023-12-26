package com.example.multifunctionbackend.repository;

import com.example.multifunctionbackend.entities.ScrappingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrappingRequestRepository extends JpaRepository<ScrappingRequest, Long> {
}

