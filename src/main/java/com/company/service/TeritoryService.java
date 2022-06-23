package com.company.service;

import com.company.dto.TeritoryDTO;
import com.company.entity.TeritoryEntity;
import com.company.enums.TeritoryStatus;
import com.company.exc.ItemNotFoundException;
import com.company.exc.TeritoryAlreadyExistsException;
import com.company.repository.TeritoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TeritoryService {
    @Autowired
    private TeritoryRepository teritoryRepository;

    public TeritoryDTO create(TeritoryDTO dto){
        Optional<TeritoryEntity> optional = teritoryRepository.findByName(dto.getName());
        if (optional.isPresent()){
            throw new TeritoryAlreadyExistsException("Teritory already exists");
        }

        TeritoryEntity entity = new TeritoryEntity();
        entity.setName(dto.getName());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(TeritoryStatus.NOTACTIVE);
        teritoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public TeritoryDTO getById(Integer id){
        TeritoryEntity entity = teritoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Teritory not found");
        });

        return toDTO(entity);
    }

    public List<TeritoryDTO> getList(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");

        List<TeritoryDTO> teritoryDTOList = new LinkedList<>();
        teritoryRepository.findAll(pageable).forEach(teritoryEntity -> {
            if (teritoryEntity.getVisible().equals(true)){
                teritoryDTOList.add(toDTO(teritoryEntity));
            }
        });

        return teritoryDTOList;
    }

    public boolean update(Integer id, String name){
        TeritoryEntity entity = teritoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Teritory not found");
        });

        if (!entity.getName().equals(name)) {
            Optional<TeritoryEntity> optional = teritoryRepository.findByName(name);
            if (optional.isPresent()) {
                throw new TeritoryAlreadyExistsException("Teritory already exists");
            }
        }

        return teritoryRepository.update(name, id) > 0;
    }

    public boolean delete(Integer id){
        teritoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Teritory not found");
        });


        return teritoryRepository.delete(false, id) > 0;
    }
    public TeritoryDTO toDTO(TeritoryEntity entity){
        TeritoryDTO dto = new TeritoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}
