package com.company.repository;

import com.company.entity.DistrEntity;
import com.company.enums.DistrStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface DistrRepository extends JpaRepository<DistrEntity, Integer> {
    Optional<DistrEntity> findByPhoneNumber(String phoneNumber);

    Optional<DistrEntity> findByUserName(String userName);

    Optional<DistrEntity> findByDistrCode(String distrCode);

    Optional<DistrEntity> findByDistrCodeAndUserName(String distrCode, String username);

    Optional<DistrEntity> findByNameAndUserName(String name, String userName);
    Optional<DistrEntity> findByDistrCodeAndName(String  distrCode, String name);

    @Transactional
    @Modifying
    @Query("update DistrEntity set visible = :visible where id = :id")
    int delete(@Param("visible") boolean b, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update DistrEntity set status = :status where distrCode = :distrCode")
    int changeStatus(@Param("status") DistrStatus status, @Param("distrCode") String distrCode);
    @Transactional
    @Modifying
    @Query("update DistrEntity set status = :status")
    int changeStatus(@Param("status") DistrStatus status);

}
