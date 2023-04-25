package com.exemplo.controleestacionamento.repositories;

import com.exemplo.controleestacionamento.models.ParkingSpotModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkinSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {

    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByParkingSpotNumber(String parkingSpotNumber);

    boolean existsByApartmentAndBlock(String apartment, String block);
}
