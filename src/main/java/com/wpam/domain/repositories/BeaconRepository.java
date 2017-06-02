package com.wpam.domain.repositories;

import com.wpam.domain.Beacon;
import com.wpam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeaconRepository extends JpaRepository<Beacon, Long> {

    Optional<Beacon> findBeaconByName(final String name);

    List<Beacon> findBeaconsByUser(User foundUser);
}
