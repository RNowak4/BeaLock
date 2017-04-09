package com.wpam.domain.repositories;

import com.wpam.domain.ChildServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildServerRepository extends JpaRepository<ChildServer, Long> {
}
