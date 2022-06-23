package com.company.repository;

import com.company.entity.CarEntity;
import com.company.enums.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CarRepository extends JpaRepository<CarEntity, Integer> {

    Optional<CarEntity> findByNameAndNumber(String name, String number);
    Optional<CarEntity> findByNumber(String number);

        @Transactional
        @Modifying
        @Query("update CarEntity set visible = :visible where id = :id")
        int delete(@Param("visible") boolean b, @Param("id") Integer id);

        @Transactional
        @Modifying
        @Query("update CarEntity set status = :status where number = :number")
        int changeStatus(@Param("status") CarStatus status, @Param("number") String number);

        @Transactional
        @Modifying
        @Query("update CarEntity set status = :status")
        int changeStatus(@Param("status") CarStatus status);



}
