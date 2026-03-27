package com.ayushmaan.ambulance.service;

import com.ayushmaan.ambulance.dto.AmbulanceDto;
import com.ayushmaan.ambulance.entity.Ambulance;
import com.ayushmaan.ambulance.entity.AmbulanceCategory;
import com.ayushmaan.ambulance.repository.AmbulanceCategoryRepository;
import com.ayushmaan.ambulance.repository.AmbulanceRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmbulanceService {
    private final AmbulanceRepository ambulanceRepository;
    private final ModelMapper modelMapper;
    private final AmbulanceCategoryRepository ambulanceCategoryRepository;

    public AmbulanceService(AmbulanceRepository ambulanceRepository, ModelMapper modelMapper,
                            AmbulanceCategoryRepository ambulanceCategoryRepository){
        this.modelMapper = modelMapper;
        this.ambulanceRepository = ambulanceRepository;
        this.ambulanceCategoryRepository = ambulanceCategoryRepository;
    }

    public String add(AmbulanceDto ambulanceDto){
        Ambulance ambulance = ambulanceRepository.findByAmbulanceType(ambulanceDto.getAmbulanceType())
                .orElse(null);
        AmbulanceCategory ambulanceCategory = ambulanceCategoryRepository.findByCategoryName(ambulanceDto.getCategory())
                .orElse(null);
        if(ObjectUtils.isEmpty(ambulanceCategory)){
            throw new RuntimeException("AmbulanceCategory does not exists");
        }
        if(ObjectUtils.isNotEmpty(ambulance)){
            throw new RuntimeException("AmbulanceType already exists");
        }
        Ambulance newAmbulance = modelMapper.map(ambulanceDto, Ambulance.class);
        newAmbulance.setCategory(ambulanceCategory);
        ambulanceRepository.save(newAmbulance);
        return "Ambulance added in the inventory";
    }

    public String delete(String ambulanceType){
        Ambulance ambulance = ambulanceRepository.findByAmbulanceType(ambulanceType)
                .orElse(null);
        if(ObjectUtils.isEmpty(ambulance)){
            throw new RuntimeException("AmbulanceType do not exists");
        }
        ambulanceRepository.delete(ambulance);
        return "Ambulance deleted from inventory";
    }

    public List<AmbulanceDto> get(){
        List<Ambulance> ambulances = ambulanceRepository.findAll();
        return ambulances.stream()
                .map(x->modelMapper.map(x,AmbulanceDto.class))
                .collect(Collectors.toList());
    }

    public AmbulanceDto update(AmbulanceDto ambulanceDto, String ambulanceType){
        Ambulance ambulance = ambulanceRepository.findByAmbulanceType(ambulanceType).orElse(null);
        if(ObjectUtils.isEmpty(ambulance)){
            throw new RuntimeException("AmbulanceType does not exists");
        }
        ambulance = modelMapper.map(ambulanceDto, Ambulance.class);
        ambulanceRepository.save(ambulance);
        return modelMapper.map(ambulance, AmbulanceDto.class);
    }
}
