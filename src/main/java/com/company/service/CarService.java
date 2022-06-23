package com.company.service;

import com.company.dto.CarDTO;
import com.company.entity.CarEntity;
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

    public CarDTO create(CarDTO dto) {
        CarValidation.isValid(dto);

        Optional<CarEntity> optional = carRepository.findByNameAndNumber(dto.getName(), dto.getNumber());

        if (optional.isPresent()) {
            throw new CarAlreadyExistsException("Car already exists");
        }

        CarEntity entity = new CarEntity();
        entity.setName(dto.getName());
        entity.setNumber(dto.getNumber());
        entity.setStatus(CarStatus.NOTACTIVE);
        entity.setCreatedDate(LocalDateTime.now());

        carRepository.save(entity);
        dto.setId(entity.getId());
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
            throw new ItemNotFoundException("Car not found");
        });

        if (entity.getVisible().equals(false)){
            throw new ItemNotFoundException("Car not found");
        }

        if (!entity.getNumber().equals(dto.getNumber())) {
            Optional<CarEntity> optional = carRepository.findByNumber(dto.getNumber());
            if (optional.isPresent()) {
                throw new AppForbiddenException("Car already exists");
            }
        }

        entity.setName(dto.getName());
        entity.setNumber(dto.getNumber());

        carRepository.save(entity);

        return dto;
    }

    public void changeStatus(String number, CarStatus status){
        carRepository.findByNumber(number).orElseThrow(() -> new ItemNotFoundException("Car not found"));

        carRepository.changeStatus(status, number);
    }


    public boolean delete(Integer id) {
        carRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Car not found");
        });

        return carRepository.delete(false, id) > 0;
    }

    public CarDTO toDTO(CarEntity entity) {
        CarDTO dto = new CarDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNumber(entity.getNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
