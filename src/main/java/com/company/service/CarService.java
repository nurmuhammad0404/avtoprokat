package com.company.service;

import com.company.dto.CarDTO;
import com.company.entity.CarEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.CarStatus;
import com.company.exc.AppForbiddenException;
import com.company.exc.CarAlreadyExistsException;
import com.company.exc.ItemNotFoundException;
import com.company.repository.CarRepository;
import com.company.validation.CarValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ProfileService profileService;

    public CarDTO create(CarDTO dto) {
        CarValidation.isValid(dto);

        Optional<CarEntity> optional = carRepository.findByNameAndNumber(dto.getName(), dto.getNumber());

        if (optional.isPresent()) {
            throw new CarAlreadyExistsException("Mashina avval kiritlgan");
        }

//        ProfileEntity profile = profileService.getById(pid);
        CarEntity entity = new CarEntity();
        entity.setName(dto.getName());
        entity.setNumber(dto.getNumber());
        entity.setStatus(CarStatus.NOTACTIVE);
//        entity.setProfileId(pid);
//        entity.setProfile(profile);
        entity.setCreatedDate(LocalDateTime.now());

        carRepository.save(entity);
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setProfile(entity.getProfile());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<CarDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<CarEntity> carEntities = carRepository.findAll(pageable);

        List<CarDTO> carDTOList = new LinkedList<>();

        for (CarEntity entity : carEntities) {
            if (entity.getVisible().equals(true)){
                carDTOList.add(toDTO(entity));
            }
        }

        return carDTOList;
    }

    public CarDTO update(Integer id, CarDTO dto) {
        CarValidation.isValid(dto);
        CarEntity entity = carRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Mashina topilmadi");
        });

        if (entity.getVisible().equals(false)){
            throw new ItemNotFoundException("Mashina topilmadi");
        }

        if (!entity.getNumber().equals(dto.getNumber())) {
            Optional<CarEntity> optional = carRepository.findByNumber(dto.getNumber());
            if (optional.isPresent()) {
                throw new AppForbiddenException("Mashina avval kiritilgan");
            }
        }

        entity.setName(dto.getName());
        entity.setNumber(dto.getNumber());

        carRepository.save(entity);

        return dto;
    }

    public void changeStatus(String number, CarStatus status){
        carRepository.findByNumber(number).orElseThrow(() -> new ItemNotFoundException("Mashina topilmadi"));

        carRepository.changeStatus(status, number);
    }


    public boolean delete(Integer id) {
        carRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Mashina topilmadi");
        });

        return carRepository.delete(false, id) > 0;
    }

    public CarDTO toDTO(CarEntity entity) {
        CarDTO dto = new CarDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNumber(entity.getNumber());
        dto.setProfileId(entity.getProfileId());
//        dto.setProfile(entity.getProfile());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
