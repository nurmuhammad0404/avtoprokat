package com.company.service;

import com.company.dto.TeritoryDTO;
import com.company.entity.TeritoryEntity;
import com.company.enums.DriverStatus;
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
        if (optional.isPresent() && optional.get().getVisible().equals(true)){
            throw new TeritoryAlreadyExistsException("Hudud avval kiritlgan");
        }

        TeritoryEntity entity = new TeritoryEntity();
        entity.setName(dto.getName());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(TeritoryStatus.NOTACTIVE);
        teritoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public TeritoryDTO getById(Integer id){
        TeritoryEntity entity = teritoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Hudud topilmadi");
        });
        if (entity.getVisible().equals(false)){
            throw new ItemNotFoundException("Hudud topilmadi");
        }

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

    public TeritoryDTO update(Integer id, TeritoryDTO dto){
        TeritoryEntity entity = teritoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Hudud toplimadi");
        });

        if (entity.getVisible().equals(false)){
            throw new ItemNotFoundException("Hudud toplimadi");
        }

        if (!entity.getName().equals(dto.getName())) {
            Optional<TeritoryEntity> optional = teritoryRepository.findByName(dto.getName());
            if (optional.isPresent() && optional.get().getVisible().equals(true)) {
                throw new TeritoryAlreadyExistsException("Hudud avval kirirtilgan");
            }
        }

        entity.setName(dto.getName());
        teritoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public void changeStatus(String teritoryName, TeritoryStatus status){
        teritoryRepository.findByName(teritoryName).orElseThrow(()->new ItemNotFoundException("Haydovchi topilmadi"));

        teritoryRepository.changeStatus(status, teritoryName);
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
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}
