package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.repository.FireStationRepository;
import com.safetynet.safetynetsystem.repository.PersonRepository;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FireStationService {
    private final FireStationRepository firestationRepository;

    public FireStationService(FireStationRepository firestationRepository, PersonRepository personRepository) {
        this.firestationRepository = firestationRepository;
    }

    public FireStation save(FireStation firestation) {
        Assert.notNull(firestation, "Fire station must not be null");
        return firestationRepository.save(firestation);
    }

    public FireStation update(FireStation firestation) {
        Assert.notNull(firestation, "Fire station must not be null");
        firestationRepository.findByAddress(firestation.getAddress()).orElseThrow(() -> new NotFoundException(
                ("Fire station not found")));
        return firestationRepository.save(firestation);
    }

    public void delete(FireStation firestation) {
        Assert.notNull(firestation, "Fire station must not be null");
        FireStation fireStation = firestationRepository.findByAddressAndStation(firestation.getAddress(), firestation.getStation()).orElseThrow(() -> new NotFoundException(
                ("Fire station not found")));
        firestationRepository.delete(firestation);
    }

}
