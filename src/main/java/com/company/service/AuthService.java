package com.company.service;


import com.company.Util.JwtUtil;
import com.company.dto.LoginDTO;
import com.company.dto.ProfileDTO;
import com.company.entity.LoginEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.exc.*;
import com.company.repository.LoginRepository;
import com.company.repository.ProfileRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private LoginRepository loginRepository;

    public ProfileDTO login(LoginDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPassword(dto.getPhone(), dto.getPswd());
        if (optional.isEmpty()) {
            throw new PhoneOrPasswordWrongException("Telefon nomer yoki parol xato");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppForbiddenException("Kirish mumkin emas");
        }

        ProfileDTO profile = new ProfileDTO();
        profile.setName(entity.getName());
        profile.setSurname(entity.getSurname());
        profile.setUserName(entity.getUserName());
        profile.setRole(entity.getRole());
        profile.setJwt(JwtUtil.doEncode(entity.getId(), entity.getRole(), 60));

        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setPhone(dto.getPhone());
        loginEntity.setPswd(dto.getPswd());
        loginEntity.setCreatedDate(LocalDateTime.now());

        loginRepository.save(loginEntity);

        return profile;
    }

//    public void registraion(RegistrationDTO dto) {
//        Optional<ProfileEntity> userName = profileRepository.findByUserName(dto.getUserName());
//        if (userName.isPresent()) {
//            throw new UserNameAlreadyExistsException("Bunday username li foydalanuvchi mavjud");
//        }
//        Optional<ProfileEntity> phone = profileRepository.findByPhone(dto.getPhone());
//        if (phone.isPresent()) {
//            throw new PhoneNumberAlreadyExistsException("Bundat telefon raqamli foydalanuvchi mavjud");
//        }
//
//        ProfileEntity entity = new ProfileEntity();
//        entity.setName(dto.getName());
//        entity.setSurname(dto.getSurname());
//        entity.setUserName(dto.getUserName());
//        entity.setPhone(dto.getPhone());
//        entity.setPassword(dto.getPassword());
//
//        entity.setRole(ProfileRole.USER);
//        entity.setStatus(ProfileStatus.NOTACTIVE);
//        entity.setCreatedDate(LocalDateTime.now());
//
//        profileRepository.save(entity);
//
//
//    }
//
//    public void verificaton(String jwt) {
//        Integer id = null;
//        try {
//            id = JwtUtil.decodeAndGetId(jwt);
//        }catch (JwtException e){
//            throw new AppBadRequestException("Verification no complited");
//        }
//    }

}
