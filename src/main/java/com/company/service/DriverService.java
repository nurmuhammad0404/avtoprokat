package com.company.service;

import com.company.dto.DriverDTO;
import com.company.entity.DistrEntity;
import com.company.entity.DriverEnitity;
import com.company.enums.DriverStatus;
import com.company.exc.ItemNotFoundException;
import com.company.exc.PhoneNumberAlreadyExistsException;
import com.company.exc.UserNameAlreadyExistsException;
import com.company.repository.DriverRepository;
import com.company.validation.DriverValidation;
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
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    public DriverDTO create(DriverDTO dto) {
        DriverValidation.isValid(dto);
        Optional<DriverEnitity> optional = driverRepository.findByPhone(dto.getPhone());
        if (optional.isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Phone already exists");
        }

        Optional<DriverEnitity> optional1 = driverRepository.findByUserName(dto.getUserName());
        if (optional1.isPresent()) {
            throw new UserNameAlreadyExistsException("Username already exists");
        }

        DriverEnitity enitity = toEntity(dto);
        driverRepository.save(enitity);
        dto.setId(enitity.getId());
        dto.setStatus(enitity.getStatus());
        dto.setVisible(enitity.getVisible());
        dto.setCreatedDate(LocalDateTime.now());

        return dto;
    }

    public List<DriverDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<DriverDTO> driverDTOList = new LinkedList<>();

        driverRepository.findAll(pageable).forEach(driverEnitity -> {
            if (driverEnitity.getVisible().equals(true)) {
                driverDTOList.add(toDTO(driverEnitity));
            }
        });

        return driverDTOList;
    }

    public DriverDTO update(Integer id, DriverDTO dto) {
        DriverEnitity entity = driverRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        System.out.println(entity);
        if (!entity.getPhone().equals(dto.getPhone())) {

            Optional<DriverEnitity> optional1 = driverRepository.findByPhone(dto.getPhone());
            if (optional1.isPresent()) {
                throw new PhoneNumberAlreadyExistsException("Phone number already exists");
            }

        }

        if (!entity.getUserName().equals(dto.getUserName())){

            Optional<DriverEnitity> optional2 = driverRepository.findByUserName(dto.getUserName());
            if (optional2.isPresent()) {
                throw new UserNameAlreadyExistsException("Username alredy exists");
            }

        }


        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUserName(dto.getUserName());
        entity.setPhone(dto.getPhone());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(LocalDateTime.now());

        driverRepository.save(entity);

        return toDTO(entity);
    }

    public Boolean delete(Integer id){
        driverRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Driver not found"));

        return driverRepository.delete(false, id) > 0;
    }

    public void changeStatus(String driverUserName, DriverStatus status){
        driverRepository.findByUserName(driverUserName).orElseThrow(()->new ItemNotFoundException("Driver not found"));

        driverRepository.changeStatus(status, driverUserName);
    }

    public DriverEnitity toEntity(DriverDTO dto) {
        DriverEnitity entity = new DriverEnitity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUserName(dto.getUserName());
        entity.setPhone(dto.getPhone());
        entity.setStatus(DriverStatus.NOTACTIVE);
        entity.setCreatedDate(LocalDateTime.now());

        return entity;
    }

    public DriverDTO toDTO(DriverEnitity enitity) {
        DriverDTO dto = new DriverDTO();
        dto.setId(enitity.getId());
        dto.setName(enitity.getName());
        dto.setSurname(enitity.getSurname());
        dto.setUserName(enitity.getUserName());
        dto.setPhone(enitity.getPhone());
        dto.setVisible(enitity.getVisible());
        dto.setStatus(enitity.getStatus());
        dto.setCreatedDate(enitity.getCreatedDate());

        return dto;
    }
}
