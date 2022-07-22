package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.model.Firestation;
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

    public Firestation save(Firestation firestation) {
        Assert.notNull(firestation, "Fire station must not be null");
        return firestationRepository.save(firestation);
    }

    public List<Firestation> getAll() {
        return firestationRepository.findAll();
    }

    public Firestation getById(Integer id) {
        return firestationRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ("Fire station not found")));
    }

    public Firestation update(Firestation firestation, Integer id) {
        Assert.notNull(firestation, "Fire station must not be null");
        return checkNotFoundWithId(firestationRepository.save(firestation), id);
    }

    public void deleteById(Integer id) {
        checkNotFoundWithId(firestationRepository.findById(id), id);
        firestationRepository.deleteById(id);
    }
}
