package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exc.AppForbiddenException;
import com.company.exc.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.validation.ProfileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto){
        ProfileValidation.isValid(dto);

        Optional<ProfileEntity> optional = profileRepository.findByUserName(dto.getUserName());
        if (optional.isPresent()){
            throw new AppForbiddenException("Bunday username mavjud");
        }
        ProfileEntity entity = toEntity(dto);
        profileRepository.save(entity);

        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<ProfileDTO> getList(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<ProfileEntity> profileEntityList = profileRepository.findAll(pageable);

        List<ProfileDTO> profileDTOList = new LinkedList<>();

        for (ProfileEntity entity : profileEntityList){
            profileDTOList.add(toDTO(entity));
        }

        return profileDTOList;
    }

    public ProfileEntity getById(String id){
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profil topilmadi");
        });
    }

    public ProfileDTO getByUserName(String userName){
        Optional<ProfileEntity> optional = profileRepository.findByUserName(userName);
        if (optional.isPresent()){
            throw new AppForbiddenException("Bunday username mavjud");
        }
        ProfileEntity entity = optional.get();

        return toDTO(entity);

    }

    public List<ProfileDTO> getByName(String name){
        List<ProfileEntity> entityList = profileRepository.findByName(name);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Profil topilmadi");
        }
        List<ProfileDTO> profileDTOList = new LinkedList<>();
        for (ProfileEntity entity : entityList){
            profileDTOList.add(toDTO(entity));
        }
        return profileDTOList;
    }

    public List<ProfileDTO> getBySurname(String surname){
        List<ProfileEntity> entityList = profileRepository.findBySurname(surname);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Profil topilmadi");
        }
        List<ProfileDTO> profileDTOList = new LinkedList<>();
        for (ProfileEntity entity : entityList){
            profileDTOList.add(toDTO(entity));
        }
        return profileDTOList;
    }

    public ProfileDTO update(String id, ProfileDTO dto){
        ProfileValidation.isValid(dto);
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profil topilmadi");
        });

        if (!entity.getUserName().equals(dto.getUserName())){
            Optional<ProfileEntity> optional = profileRepository.findByUserName(dto.getUserName());
            if (optional.isPresent()){
                throw new AppForbiddenException("Bunday username mavjud");
            }
        }

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUserName(dto.getUserName());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        profileRepository.save(entity);
        dto.setUpdateDate(entity.getUpdateDate());

        return dto;
    }

    public boolean delete(String id){
        profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profil topilmadi");
        });

        profileRepository.deleteById(id);
        return true;
    }

    public ProfileEntity toEntity(ProfileDTO dto){
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUserName(dto.getUserName());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(ProfileRole.USER);
        entity.setCreatedDate(LocalDateTime.now());
        return entity;
    }

    public ProfileDTO toDTO(ProfileEntity entity){
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUserName(entity.getUserName());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateDate(entity.getUpdateDate());

        return dto;
    }
}
