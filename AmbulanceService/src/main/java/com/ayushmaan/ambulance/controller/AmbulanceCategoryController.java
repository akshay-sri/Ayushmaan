package com.ayushmaan.ambulance.controller;

import com.ayushmaan.ambulance.dto.AmbulanceCategoryDto;
import com.ayushmaan.ambulance.service.AmbulanceCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambulance-category")
@Tag(name = "Ambulance-category Apis", description = "Operations related to ambulance-category")
public class AmbulanceCategoryController {

    private final AmbulanceCategoryService ambulanceCategoryService;

    public AmbulanceCategoryController(AmbulanceCategoryService ambulanceCategoryService) {
        this.ambulanceCategoryService = ambulanceCategoryService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new ambulance category", description = "Adds new ambulance category in the database")
    public ResponseEntity<String> addAmbulanceCategory(@RequestBody AmbulanceCategoryDto ambulanceCategoryDto) {
        return new ResponseEntity<>(ambulanceCategoryService.add(ambulanceCategoryDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{categoryName}")
    @Operation(summary = "Delete an ambulance category", description = "Deletes an ambulance category from the database")
    public ResponseEntity<String> deleteAmbulanceCategory(@PathVariable String categoryName) {
        return new ResponseEntity<>(ambulanceCategoryService.delete(categoryName), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{categoryName}")
    @Operation(summary = "Update an ambulance category", description = "Update an ambulance category in the database")
    public ResponseEntity<String> updateAmbulanceCategory(@RequestBody AmbulanceCategoryDto ambulanceCategoryDto,
                                                        @PathVariable String categoryName) {
        return new ResponseEntity<>(ambulanceCategoryService.update(ambulanceCategoryDto, categoryName), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<AmbulanceCategoryDto>> getAllAmbulanceCategories(){
        return new ResponseEntity<>(ambulanceCategoryService.get(), HttpStatus.OK);
    }
}
