package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.repository.FireStationRepository;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.safetynet.safetynetsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class FireStationService {
    private final FireStationRepository firestationRepository;

    public FireStationService(FireStationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    public FireStation save(FireStation firestation) {
        Assert.notNull(firestation, "Fire station must not be null");
        return firestationRepository.save(firestation);
    }

    public List<FireStation> getAll() {
        return firestationRepository.findAll();
    }

    public FireStation getById(Integer id) {
        return firestationRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ("Fire station not found")));
    }

    public FireStation update(FireStation firestation, Integer id) {
        Assert.notNull(firestation, "Fire station must not be null");
        firestationRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ("Fire station not found")));
        return firestationRepository.save(firestation);
    }

    public void deleteById(Integer id) {
        checkNotFoundWithId(firestationRepository.findById(id), id);
        firestationRepository.deleteById(id);
    }
}
