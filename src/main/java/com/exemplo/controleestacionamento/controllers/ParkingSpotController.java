package com.exemplo.controleestacionamento.controllers;


import com.exemplo.controleestacionamento.dtos.ParkinSpotDto;
import com.exemplo.controleestacionamento.models.ParkingSpotModel;
import com.exemplo.controleestacionamento.repositories.ParkinSpotRepository;
import com.exemplo.controleestacionamento.services.ParkinSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkinSpotService parkinSpotService;

    public ParkingSpotController(ParkinSpotService parkinSpotService){
        this.parkinSpotService = parkinSpotService;
    }


    @PostMapping
    public ResponseEntity<Object> saveParkingSpot( @RequestBody @Valid ParkinSpotDto parkinSpotDto){

        if(parkinSpotService.existsByLicensePlateCar(parkinSpotDto.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Lincese Plate Car is already in use!");
        }
        if(parkinSpotService.existsByParkingSpotNumber(parkinSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot number is already in use!");
        }
        if(parkinSpotService.existsByApartmentAndBlock(parkinSpotDto.getApartment(), parkinSpotDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already registered for this apartment/block!");
        }


        var parkinSpotModel =  new ParkingSpotModel();
        BeanUtils.copyProperties(parkinSpotDto, parkinSpotModel);
        parkinSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UCT")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkinSpotService.save(parkinSpotModel));
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
        return ResponseEntity.status(HttpStatus.OK).body(parkinSpotService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable (value = "id") UUID id){

        Optional<ParkingSpotModel> parkingSpotModelOptional = parkinSpotService.findById(id);

        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkinSpotService.findById(id);

        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found!");
        }
        parkinSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking spot deleted successfully!");

    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkinSpotDto parkinSpotDto){

        Optional<ParkingSpotModel> parkingSpotModelOptional = parkinSpotService.findById(id);

        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found!");
        }

        var parkingSpotModel = parkingSpotModelOptional.get();

//         parkingSpotModel.setParkingSpotNumber(parkinSpotDto.getParkingSpotNumber());
//         parkingSpotModel.setLicensePlateCar(parkinSpotDto.getLicensePlateCar());
//         parkingSpotModel.setModelCar(parkinSpotDto.getModelCar());
//         parkingSpotModel.setBrandCar(parkinSpotDto.getBrandCar());
//         parkingSpotModel.setColorCar(parkinSpotDto.getColorCar());
//         parkingSpotModel.setResponsibleName(parkinSpotDto.getResponsibleName());
//         parkingSpotModel.setApartment(parkinSpotDto.getApartment());
//         parkingSpotModel.setBlock(parkinSpotDto.getBlock());

         BeanUtils.copyProperties(parkinSpotDto, parkingSpotModel);
         parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
         parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());

         return ResponseEntity.status(HttpStatus.OK).body(parkinSpotService.save(parkingSpotModel));

    }








}
