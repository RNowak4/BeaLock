package com.wpam.domain.repositories;

import com.wpam.domain.ChildServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildServerRepository extends JpaRepository<ChildServer, Long> {

    Optional<ChildServer> findOneByIpAndPort(String ip, String port);

    List<ChildServer> findAllByIsActive(boolean b);
}