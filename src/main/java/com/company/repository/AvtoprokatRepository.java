package com.company.repository;

import com.company.entity.AvtoprokatEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AvtoprokatRepository extends JpaRepository<AvtoprokatEntity, Integer> {
    Optional<AvtoprokatEntity> findByDistrCode(String distrCode);
    Page<AvtoprokatEntity> findAllByCreatedDate(LocalDateTime createdDate, Pageable pageable);
    List<AvtoprokatEntity> findByCreatedDateBefore(LocalDateTime localDateTime);

    List<AvtoprokatEntity> findByCreatedDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);


}
