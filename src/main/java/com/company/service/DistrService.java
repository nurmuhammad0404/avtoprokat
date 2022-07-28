package com.company.service;

import com.company.dto.DistrDTO;
import com.company.entity.DistrEntity;
import com.company.entity.ProfileEntity;
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

//    @Autowired
//    private ProfileService profileService;
    public DistrDTO create(DistrDTO dto) {
        DistrValidation.isValid(dto);
        Optional<DistrEntity> optional = distrRepository.findByPhoneNumber(dto.getPhone());

        if (optional.isPresent() && optional.get().getVisible().equals(true)) {
            throw new PhoneNumberAlreadyExistsException("Bunday telefon raqamli distr mavjud");
        }


        Optional<DistrEntity> optional1 = distrRepository.findByDistrCode(dto.getDistrCode());
        if (optional1.isPresent() && optional.get().getVisible().equals(true)){
            throw new DistrCodeAlreadyExistsException("Bunday codli distr mavjud");
        }

        Optional<DistrEntity>optional2 = distrRepository.findByUserName(dto.getUserName());
        if(optional2.isPresent() && optional.get().getVisible().equals(true)){
            throw new UserNameAlreadyExistsException("Bunday usernameli distr mavjud");
        }

//        ProfileEntity profile = profileService.getById(pid);

        DistrEntity entity = toEntity(dto);
//        entity.setProfileId(pid);
//        entity.setProfile(profile);
        distrRepository.save(entity);

        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setProfileId(entity.getProfileId());
        dto.setProfile(entity.getProfile());
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

        DistrEntity entity = distrRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Distr topilmadi"));

        if (entity.getVisible().equals(false)){
            throw new ItemNotFoundException("Distr topilmadi");
        }

        if (!entity.getPhoneNumber().equals(dto.getPhone())){
            Optional<DistrEntity> optional = distrRepository.findByPhoneNumber(dto.getPhone());
            if (optional.isPresent() && optional.get().getVisible().equals(true)){
                throw new PhoneNumberAlreadyExistsException("Bunday telefon nomerli distr mavjud");
            }
        }

        if (!entity.getUserName().equals(dto.getUserName())){
            Optional<DistrEntity> optional = distrRepository.findByUserName(dto.getUserName());
            if (optional.isPresent() && optional.get().getVisible().equals(true)){
                throw new UserNameAlreadyExistsException("Bunday usernameli distr mavjud");
            }
        }

        if (!entity.getDistrCode().equals(dto.getDistrCode())){
            Optional<DistrEntity> optional = distrRepository.findByDistrCode(dto.getDistrCode());
            if (optional.isPresent() && optional.get().getVisible().equals(true)){
                throw new UserNameAlreadyExistsException("Bunday distrCodeli distr mavjud");
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
        distrRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Distr topilmadi"));

        return distrRepository.delete(false, id) > 0;

    }


    public void changeStatus(String distrCode, DistrStatus status){
        distrRepository.findByDistrCode(distrCode).orElseThrow(() -> new ItemNotFoundException("Distr topilmadi"));
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
//        dto.setProfileId(entity.getProfileId());
//        dto.setProfile(entity.getProfile());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}

