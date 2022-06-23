package com.company.repository;


import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByPhone(String phone);
    Optional<ProfileEntity> findByUserName(String userName);

    Page<ProfileEntity> findAll(Pageable pageable);

    List<ProfileEntity> findByName(String name);

    List<ProfileEntity> findBySurname(String surname);

    Optional<ProfileEntity> findByPhoneAndPassword(String phone, String password);

}
