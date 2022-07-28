package com.company.repository;

import com.company.entity.DriverEnitity;
import com.company.enums.DriverStatus;
import com.company.service.DriverService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Driver;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<DriverEnitity, Integer> {

    Optional<DriverEnitity> findByUserName(String userName);
    Optional<DriverEnitity> findByPhone(String phone);
    Optional<DriverEnitity> findByName(String name);

    Optional<DriverEnitity> findByNameAndUserName(String name, String userName);

    @Transactional
    @Modifying
    @Query("update DistrEntity set visible = :visible where id = :id")
    int delete(@Param("visible") boolean b, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update DriverEnitity set status = :status where userName = :userName")
    int changeStatus(@Param("status") DriverStatus status, @Param("userName") String userName);

    @Transactional
    @Modifying
    @Query("update DriverEnitity set status = :status where userName = :userName")
    int changeStatus(@Param("status") DriverStatus status);



}
