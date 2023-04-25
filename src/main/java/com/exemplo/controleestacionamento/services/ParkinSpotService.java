package com.exemplo.controleestacionamento.services;

import com.exemplo.controleestacionamento.models.ParkingSpotModel;
import com.exemplo.controleestacionamento.repositories.ParkinSpotRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkinSpotService {
    final ParkinSpotRepository parkinSpotRepository;

    public ParkinSpotService(ParkinSpotRepository parkinSpotRepository) {
        this.parkinSpotRepository = parkinSpotRepository;
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkinSpotModel) {
        return parkinSpotRepository.save(parkinSpotModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkinSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }
    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkinSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkinSpotRepository.existsByApartmentAndBlock(apartment,block );

    }

    public List<ParkingSpotModel> findAll() {
        return parkinSpotRepository.findAll();
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        return parkinSpotRepository.findById(id);
    }

    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkinSpotRepository.delete(parkingSpotModel);
    }


}
