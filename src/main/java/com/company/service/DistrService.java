package com.company.service;

import com.company.dto.DistrDTO;
import com.company.entity.DistrEntity;
import com.company.enums.DistrStatus;
import com.company.exc.DistrCodeAlreadyExistsException;
import com.company.exc.ItemNotFoundException;
import com.company.exc.PhoneNumberAlreadyExistsException;
import com.company.exc.UserNameAlreadyExistsException;
import com.company.repository.DistrRepository;
import com.company.validation.DistrValidation;
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
public class DistrService {
    @Autowired
    private DistrRepository distrRepository;
    public DistrDTO create(DistrDTO dto) {
        DistrValidation.isValid(dto);
        Optional<DistrEntity> optional = distrRepository.findByPhoneNumber(dto.getPhone());

        if (optional.isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        Optional<DistrEntity> optional1 = distrRepository.findByDistrCode(dto.getDistrCode());
        if (optional1.isPresent()){
            throw new DistrCodeAlreadyExistsException("Distr code already exists");
        }

        Optional<DistrEntity>optional2 = distrRepository.findByUserName(dto.getUserName());
        if(optional2.isPresent()){
            throw new UserNameAlreadyExistsException("Username already exists");
        }

        DistrEntity entity = toEntity(dto);

        distrRepository.save(entity);

        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<DistrDTO> list(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<DistrDTO> distrDTOList = new LinkedList<>();

        distrRepository.findAll(pageable).forEach(entity -> {
            if (entity.getVisible().equals(true)){
                distrDTOList.add(toDTO(entity));
            }

        });

        return distrDTOList;
    }

    public DistrDTO update(Integer id, DistrDTO dto) {
        DistrValidation.isValid(dto);

        DistrEntity entity = distrRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Distr not found"));

        if (entity.getVisible().equals(false)){
            throw new ItemNotFoundException("Distr not found");
        }

        if (!entity.getPhoneNumber().equals(dto.getPhone())){
            Optional<DistrEntity> optional = distrRepository.findByPhoneNumber(dto.getPhone());
            if (optional.isPresent()){
                throw new PhoneNumberAlreadyExistsException("Phone number already exists");
            }
        }

        if (!entity.getUserName().equals(dto.getUserName())){
            Optional<DistrEntity> optional = distrRepository.findByUserName(dto.getUserName());
            if (optional.isPresent()){
                throw new UserNameAlreadyExistsException("Username already exists");
            }
        }

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUserName(dto.getUserName());
        entity.setDistrCode(dto.getDistrCode());
        entity.setPhoneNumber(dto.getPhone());


        distrRepository.save(entity);
        return toDTO(entity);
    }

    public boolean delete(Integer id){
        distrRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));

        return distrRepository.delete(false, id) > 0;

    }


    public void changeStatus(String distrCode, DistrStatus status){
        distrRepository.findByDistrCode(distrCode).orElseThrow(() -> new ItemNotFoundException("Distr not found"));
        distrRepository.changeStatus(status, distrCode);
    }
    public DistrEntity toEntity(DistrDTO dto) {
        DistrEntity entity = new DistrEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUserName(dto.getUserName());
        entity.setDistrCode(dto.getDistrCode());
        entity.setPhoneNumber(dto.getPhone());
        entity.setStatus(DistrStatus.NOTACTIVE);
        entity.setCreatedDate(LocalDateTime.now());

        return entity;
    }

    public DistrDTO toDTO(DistrEntity entity) {
        DistrDTO dto = new DistrDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUserName(entity.getUserName());
        dto.setDistrCode(entity.getDistrCode());
        dto.setPhone(entity.getPhoneNumber());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}

