package com.ayushmaan.ambulance.service;

import com.ayushmaan.ambulance.dto.AmbulanceCategoryDto;
import com.ayushmaan.ambulance.entity.AmbulanceCategory;
import com.ayushmaan.ambulance.repository.AmbulanceCategoryRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmbulanceCategoryService {

    private final AmbulanceCategoryRepository ambulanceCategoryRepository;
    private final ModelMapper modelMapper;

    public AmbulanceCategoryService(AmbulanceCategoryRepository ambulanceCategoryRepository, ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.ambulanceCategoryRepository = ambulanceCategoryRepository;
    }

    public String add(AmbulanceCategoryDto ambulanceCategoryDto){
        AmbulanceCategory ambulanceCategory = ambulanceCategoryRepository.findByCategoryName(ambulanceCategoryDto.getCategoryName())
                .orElse(null);
        if(ObjectUtils.isNotEmpty(ambulanceCategory)){
            throw new RuntimeException("AmbulanceCategory already exists");
        }
        AmbulanceCategory newAmbulanceCategory = modelMapper.map(ambulanceCategoryDto, AmbulanceCategory.class);
        ambulanceCategoryRepository.save(newAmbulanceCategory);
        return "Ambulance added in the inventory";
    }

    public String delete(String categoryName){
        AmbulanceCategory ambulanceCategory = ambulanceCategoryRepository.findByCategoryName(categoryName)
                .orElse(null);
        if(ObjectUtils.isEmpty(ambulanceCategory)){
            throw new RuntimeException("AmbulanceCategory do not exists");
        }
        ambulanceCategoryRepository.delete(ambulanceCategory);
        return "Ambulance category deleted from inventory";
    }

    public List<AmbulanceCategoryDto> get(){
        List<AmbulanceCategory> ambulanceCategories = ambulanceCategoryRepository.findAll();
        return ambulanceCategories.stream()
                .map(x->modelMapper.map(x,AmbulanceCategoryDto.class))
                .collect(Collectors.toList());
    }

    public String update(AmbulanceCategoryDto ambulanceCategoryDto, String categoryName) {
        AmbulanceCategory ambulanceCategory = ambulanceCategoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category does not exists"));
       if(ObjectUtils.isNotEmpty(ambulanceCategoryDto.getCategoryName())){
           ambulanceCategory.setCategoryName(ambulanceCategoryDto.getCategoryName());
       }
       if(ObjectUtils.isNotEmpty(ambulanceCategoryDto.getDescription())){
           ambulanceCategory.setDescription(ambulanceCategoryDto.getDescription());
       }
       ambulanceCategoryRepository.save(ambulanceCategory);
       return "Ambulance category details updated!";
    }
}
