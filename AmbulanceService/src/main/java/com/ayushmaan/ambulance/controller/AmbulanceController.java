package com.ayushmaan.ambulance.controller;

import com.ayushmaan.ambulance.dto.AmbulanceDto;
import com.ayushmaan.ambulance.service.AmbulanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambulance")
@Tag(name = "Ambulance Apis", description = "Operations related to ambulance")
public class AmbulanceController {

    private final AmbulanceService ambulanceService;

    public AmbulanceController(AmbulanceService ambulanceService) {
        this.ambulanceService = ambulanceService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new ambulance", description = "Adds new ambulance in the database")
    public ResponseEntity<String> addAmbulance(@RequestBody AmbulanceDto ambulanceDto) {
        return new ResponseEntity<>(ambulanceService.add(ambulanceDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{ambulanceType}")
    @Operation(summary = "Delete an ambulance", description = "Deletes an ambulance from the database")
    public ResponseEntity<String> deleteAmbulance(@PathVariable String ambulanceType) {
        return new ResponseEntity<>(ambulanceService.delete(ambulanceType), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{ambulanceType}")
    @Operation(summary = "Update an ambulance", description = "Update an ambulance in the database")
    public ResponseEntity<String> updateAmbulance(@RequestBody AmbulanceDto ambulanceDto,
                                                  @PathVariable String ambulanceType) {
        return new ResponseEntity<>(ambulanceService.update(ambulanceDto, ambulanceType), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<AmbulanceDto>> getAllAmbulances(){
        return new ResponseEntity<>(ambulanceService.get(), HttpStatus.OK);
    }
}
