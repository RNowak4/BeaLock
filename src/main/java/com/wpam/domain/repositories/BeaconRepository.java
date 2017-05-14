package com.wpam.domain.repositories;

import com.wpam.domain.Beacon;
import com.wpam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeaconRepository extends JpaRepository<Beacon, Long> {

    Beacon findBeaconByName(final String name);

    List<Beacon> findBeaconsByUser(User foundUser);
}
