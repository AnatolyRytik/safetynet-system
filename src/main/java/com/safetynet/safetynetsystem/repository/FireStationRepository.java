package com.safetynet.safetynetsystem.repository;

import com.safetynet.safetynetsystem.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FireStationRepository extends JpaRepository<FireStation, Integer> {
    Optional<List<FireStation>> findByStation(String station);

    Optional<List<FireStation>> findByAddress(String address);

    Optional<FireStation> findByAddressAndStation(String address, String station);
}
